package br.com.agrostok.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import br.com.agrostok.dto.IngredienteDto;
import br.com.agrostok.entity.*;
import br.com.agrostok.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.agrostok.dto.ProdutoDto;
import br.com.agrostok.dto.SaledProductDto;
import br.com.agrostok.dto.ValidationDto;
import br.com.agrostok.dto.filter.PaginacaoDto;
import br.com.agrostok.exception.AppRuntimeException;
import br.com.agrostok.exception.ValidationException;
import br.com.agrostok.util.AppUtil;
import br.com.gestaoprocesso.enums.PaginacaoEnum;

import static java.util.stream.Collectors.groupingBy;

@Service
public class ProductService {

//	private static final Logger logger = LogManager.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private SaleProductRepository saleProductRepository;

    @Autowired
    private ProductIngredientRepository productIngredientRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private StockService stockService;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public void createProduct(ProdutoDto produtoDto) {
        validateIfProductAlreadyExists(produtoDto);

        try {

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Product product = convertToEntity(produtoDto);

            Set<ConstraintViolation<Product>> violations = validator.validate(product);

            if (Objects.nonNull(violations) && !violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }

            List<ProductIngrediente> ingredientes = product.getIngredientes();
            product.setIngredientes(null);
            productRepository.save(product);

            ingredientes.stream().forEach(pi -> {
                ProductIngrediente productIngrediente = new ProductIngrediente();
                productIngrediente.setProduct(product)
                        .setIngrediente(pi.getIngrediente());
                productIngredientRepository.save(productIngrediente);
            });
//            stockService.createStock(product);
        } catch (Exception e) {
            throw new AppRuntimeException(AppUtil.generateRandomString());
        }

    }

    public void updateProduct(ProdutoDto produtoDto) {
        validateIfProductAlreadyExists(produtoDto);
        try {
            Product productToUpdate = findById(produtoDto.getId());
            productToUpdate.setName(produtoDto.getName());
            productToUpdate.setValue(produtoDto.getValue());
            productToUpdate.setValueSale(produtoDto.getValueSale());
            productToUpdate.setDescriptionValue(produtoDto.getDescriptionValue());
            productToUpdate.setDescriptionValue2(produtoDto.getDescriptionValue2());
            productToUpdate.setValueSale2(produtoDto.getValueSale2());
            productToUpdate.setMinStock(produtoDto.getMinStock());
            productToUpdate.setTemIngrediente(produtoDto.isTemIngrediente());
            productRepository.save(productToUpdate);
        } catch (Exception e) {
            throw new AppRuntimeException(AppUtil.generateRandomString());
        }

    }

    public List<ProdutoDto> listAll(PaginacaoDto paginacaoDto) {
        try {
            PageRequest paginacao = PageRequest.of(PaginacaoEnum.getPage(paginacaoDto.getPagina()),
                    PaginacaoEnum.getTotalRegistros(paginacaoDto.getQtdRegistros()));

            Page<Product> pageProducts = productRepository.findByFiltros(userService.getLoggerUser().getId(),
                    paginacao);
            if (!pageProducts.getContent().isEmpty()) {
                return pageProducts.getContent().stream().map(product -> {
                    ProdutoDto produtoDto = new ProdutoDto();
                    return convertProductToDTO(product, produtoDto);

                }).collect(Collectors.toList());
            }
        } catch (Exception e) {
            throw new AppRuntimeException(AppUtil.generateRandomString());
        }

        return new ArrayList<ProdutoDto>();
    }

    private ProdutoDto convertProductToDTO(Product product, ProdutoDto produtoDto) {
        produtoDto.setTwoPrice(product.getTwoPrice())
                .setTemIngrediente(product.getTemIngrediente())
                .setDescriptionValue(product.getDescriptionValue())
                .setDescriptionValue2(product.getDescriptionValue2())
                .setValueSale(product.getValueSale())
                .setValueSale2(product.getValueSale2())
                .setName(product.getName())
                .setValue(product.getValue())
//                .setEstoque(product.getStock() != null ? product.getStock().getCount() : null)
                .setId(product.getId());


        List<ProductIngrediente> ingredientes = productIngredientRepository.findByProductId(product.getId());
        if (Objects.nonNull(ingredientes) && !ingredientes.isEmpty()) {
            List<IngredienteDto> ingredienteDtos = ingredientes.stream().map(i -> {
                IngredienteDto ingredienteDto = new IngredienteDto();
                ingredienteDto.setQuantidade(i.getIngrediente().getQuantidade())
                        .setName(i.getIngrediente().getName())
                        .setValue(i.getIngrediente().getValue());
                return ingredienteDto;
            }).collect(Collectors.toList());
            produtoDto.setIngredientes(ingredienteDtos);
        }
        return produtoDto;
    }

    @Transactional
    public ProdutoDto findDtoById(Long id) {
        try {
            Optional<Product> product = productRepository.findById(id);
            if (product.isPresent()) {
                return convertProductToDTO(product.get(), new ProdutoDto());
            }

        } catch (Exception e) {
            throw new AppRuntimeException(AppUtil.generateRandomString());
        }
        return null;
    }

    public Product findById(Long id) {
        try {
            Optional<Product> product = productRepository.findById(id);
            if (product.isPresent()) {
                return product.get();
            }

        } catch (Exception e) {
            throw new AppRuntimeException(AppUtil.generateRandomString());
        }
        return null;
    }

    private void validateIfProductAlreadyExists(ProdutoDto produtoDto) {
        Optional<Product> productFound = productRepository.findByNameAndUserCreatedId(produtoDto.getName().trim(),
                userService.getLoggerUser().getId());
        if (productFound.isPresent() && !productFound.get().getId().equals(produtoDto.getId())) {
            ValidationDto validationDto = new ValidationDto();
            validationDto.setMensagem("Nome de produto já existente.");
            throw new ValidationException(Arrays.asList(validationDto));
        }
    }

    private Product convertToEntity(ProdutoDto produtoDto) {

        Product product = modelMapper.map(produtoDto, Product.class);
        product.setTemIngrediente(produtoDto.isTemIngrediente());
        product.setCreatedDate(LocalDateTime.now());
        product.setUserCreatedId(Long.valueOf(userService.getLoggerUser().getId()));

        return product;
    }

    public List<SaledProductDto> findProductsWithTotalSaled() {
        LocalDateTime startDate = LocalDateTime.of(2000, 1, 1, 0, 0, 0);
        List<ProdutoDto> productsToSum = new ArrayList<>();
//            List<SaleProduct> productDtos = productRepository.findProductAndSaleValue(userService.getLoggerUser().getId());
        List<SaledProductDto> returnList = new ArrayList<>();
        List<Product> products = saleRepository.findProductsSalesByMonth(startDate, null);
        products.stream().forEach(product -> {

            List<ProductIngrediente> productIngredientes = productIngredientRepository.findByProductId(product.getId());

            List<Long> idsIngredientes = productIngredientes.stream().map(i -> i.getIngrediente().getId()).collect(Collectors.toList());

            BigDecimal totalVendidoPorMes = BigDecimal.ZERO;
            BigDecimal totalDespesaPorMes = BigDecimal.ZERO;
            if (idsIngredientes.isEmpty()) {
                totalDespesaPorMes = saleRepository.findTotalDespesaCustoFixo(product.getId(), startDate, null);
            } else {
                for (ProductIngrediente productIngrediente : productIngredientes) {
                    List<ProdutoDto> dates = new ArrayList<>();

                    List<Product> productsByIngrediente = getProductsByIngrediente(productIngrediente);
                    productsByIngrediente.stream().forEach(productByIng -> {
                        ProdutoDto dto = new ProdutoDto();
                        dto.setDate(productByIng.getCreatedDate()).setId(productByIng.getId());
                        dates.add(dto);
                    });

                    BigDecimal totalPorIngrediente = BigDecimal.ZERO;
                    LocalDateTime dateToSearch = null;
                    LocalDateTime dateEndToSearch = null;
                    dates.sort(Comparator.comparing(ProdutoDto::getDate));

                    boolean primeiroProdutoCadastrado = false;
                    for (ProdutoDto produto : dates) {

                        if (produto.getId().equals(product.getId())) {

                            if (isPrimeiroProdutoCadastrado(product, dates)) {
                                primeiroProdutoCadastrado = true;
                                dateToSearch = startDate;
                                dateEndToSearch = getDataFimPrimeiroProdutoCadastrado(dates);
                            } else {
                                int index = getIndexDoProdutoAtual(product, dates);
                                dateToSearch = dates.get(index).getDate();
                                dateEndToSearch = getDataFimProdutosCadastrados(dates, index);
                            }

                            totalPorIngrediente = saleRepository.findTotalDespesaPorMultiplosProdutos(productIngrediente.getIngrediente().getId(), dateToSearch, dateEndToSearch);
                            if(Objects.isNull(totalPorIngrediente)){
                                totalPorIngrediente = BigDecimal.ZERO;
                            }
                            if (primeiroProdutoCadastrado) {
                                totalDespesaPorMes = totalDespesaPorMes.add(Objects.isNull(totalPorIngrediente) ? BigDecimal.ZERO : totalPorIngrediente);
                            } else {
                                List<ProdutoDto> productsComDataAnterior = dates.stream().filter(date -> date.getDate().compareTo(product.getCreatedDate()) < 0).collect(Collectors.toList());
                                totalDespesaPorMes = totalDespesaPorMes.add(totalPorIngrediente.divide(new BigDecimal(productsComDataAnterior.size()+1), 2, RoundingMode.HALF_UP));

                                for (ProdutoDto dto : productsComDataAnterior) {
                                    dto.setValueToSum(totalDespesaPorMes);
                                }
                                productsToSum.addAll(productsComDataAnterior);
                            }
                        }
                    }
                }
            }


            totalVendidoPorMes = saleRepository.findTotalVendido(Arrays.asList(product.getId()), startDate, null);
            SaledProductDto dto = new SaledProductDto();
            dto.setTotal(Objects.isNull(totalVendidoPorMes) ? BigDecimal.ZERO : totalVendidoPorMes);
            dto.setDespesa(Objects.isNull(totalDespesaPorMes) ? BigDecimal.ZERO : totalDespesaPorMes);
            dto.setReceita(Objects.isNull(totalVendidoPorMes) ? BigDecimal.ZERO : totalVendidoPorMes);
            dto.setLiquido(dto.getReceita().subtract(dto.getDespesa()));
            dto.setProductName(product.getName());
            dto.setProductId(product.getId());
            returnList.add(dto);
        });

        returnList.stream().forEach(item -> {
            List<ProdutoDto> produtoDtos = productsToSum.stream().filter(productToSum -> productToSum.getId().equals(item.getProductId())).collect(Collectors.toList());
            produtoDtos.forEach(p->{
                item.setDespesa(item.getDespesa().add(p.getValueToSum()));
            });

        });

        return returnList;

    }

    private LocalDateTime getDataFimPrimeiroProdutoCadastrado(List<ProdutoDto> dates) {
        LocalDateTime dateEndToSearch;
        if (dates.size() > 1) {
            dateEndToSearch = dates.get(1).getDate();
        } else {
            dateEndToSearch = null;
        }
        return dateEndToSearch;
    }

    private LocalDateTime getDataFimProdutosCadastrados(List<ProdutoDto> dates, int index) {
        LocalDateTime dateEndToSearch;
        if (index + 1 < dates.size()) {
            dateEndToSearch = dates.get(index + 1).getDate();
        } else {
            dateEndToSearch = null;
        }
        return dateEndToSearch;
    }

    private boolean isPrimeiroProdutoCadastrado(Product product, List<ProdutoDto> produtoDto) {
        return produtoDto.get(0).getId().equals(product.getId());
    }

    private int getIndexDoProdutoAtual(Product product, List<ProdutoDto> dates) {
        int index = 0;
        for (ProdutoDto produtoToFind : dates) {
            if (produtoToFind.getId().equals(product.getId())) {
                break;
            }
            index++;
        }
        return index;
    }

    private List<Product> getProductsByIngrediente(ProductIngrediente productIngrediente) {
        List<Product> productsByIngrediente = new ArrayList<>();
        List<ProductIngrediente> productIngredienteList = productIngrediente.getIngrediente().getProducts();
        productsByIngrediente.addAll(productIngredienteList.stream().map(ProductIngrediente::getProduct).collect(Collectors.toList()));
        return productsByIngrediente;
    }

    @Transactional
    public List<SaledProductDto> findProductsWithTotalSaledGroupedByMonth(LocalDate startDateWithoutHours) {
        try {
            LocalDateTime startDate = LocalDateTime.of(startDateWithoutHours, LocalTime.MIN);
            LocalDateTime endDate = startDate.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59);

            List<SaledProductDto> returnList = new ArrayList<>();
            List<Product> products = saleRepository.findProductsSalesByMonth(startDate, endDate);
            products.stream().forEach(product -> {
                List<ProductIngrediente> ingredientes = productIngredientRepository.findByProductId(product.getId());
                List<Long> idsIngredientes = ingredientes.stream().map(i -> i.getIngrediente().getId()).collect(Collectors.toList());

                BigDecimal totalVendidoPorMes = BigDecimal.ZERO;
                BigDecimal totalDespesaPorMes = BigDecimal.ZERO;
                if (idsIngredientes.isEmpty()) {
                    totalDespesaPorMes = saleRepository.findTotalDespesaCustoFixo(product.getId(), startDate, endDate);
                } else {
                    totalDespesaPorMes = saleRepository.findTotalDespesa(idsIngredientes, startDate, endDate);
                }
                totalVendidoPorMes = saleRepository.findTotalVendido(Arrays.asList(product.getId()), startDate, endDate);
                SaledProductDto dto = new SaledProductDto();
                dto.setTotal(Objects.isNull(totalVendidoPorMes) ? BigDecimal.ZERO : totalVendidoPorMes);
                dto.setDespesa(Objects.isNull(totalDespesaPorMes) ? BigDecimal.ZERO : totalDespesaPorMes);
                dto.setReceita(Objects.isNull(totalVendidoPorMes) ? BigDecimal.ZERO : totalVendidoPorMes);
                dto.setLiquido(dto.getReceita().subtract(dto.getDespesa()));
                dto.setProductName(product.getName());
                returnList.add(dto);
            });

            return returnList;

        } catch (Exception e) {
            System.out.println(e);
            throw new AppRuntimeException(AppUtil.generateRandomString());
        }
    }

    private Predicate<SaledProductDto> findByProductId(SaledProductDto dto) {
        return dtoIterate -> dtoIterate.getProductId() == dto.getProductId();
    }

}
