package br.com.agrostok.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import br.com.agrostok.dto.IngredienteDto;
import br.com.agrostok.entity.*;
import br.com.agrostok.repository.ProductIngredientRepository;
import br.com.agrostok.repository.SaleProductRepository;
import br.com.agrostok.repository.SaleRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
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
import br.com.agrostok.repository.ProductRepository;
import br.com.agrostok.util.AppUtil;
import br.com.gestaoprocesso.enums.PaginacaoEnum;

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
        try {
//            List<SaleProduct> productDtos = productRepository.findProductAndSaleValue(userService.getLoggerUser().getId());
            List<Sale> sales = saleRepository.findAll();
            Map<Long, SaledProductDto> mapaVendaPorProduto = new HashMap<>();
            for (Sale sale : sales) {
                List<SaleProduct> saleProducts = saleProductRepository.findBySaleId(sale.getId());
                saleProducts.stream().forEach(s -> {
                    SaledProductDto dto = new SaledProductDto();
                    boolean isSalgadinho = false;
                    if (Objects.isNull(s.getCusto())) {
                        List<ProductIngrediente> productIngredientes = this.productIngredientRepository.findByProductId(s.getProduct().getId());

                        List<Ingrediente> ingredientes = productIngredientes.stream().map(ProductIngrediente::getIngrediente).collect(Collectors.toList());
                        List<Stock> stocks = new ArrayList<>();
                        ingredientes.stream().forEach(i -> {
                            stocks.addAll(i.getStocks());
                        });
                        BigDecimal despesa = stocks.stream().map(Stock::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);

                        dto.setDespesa(despesa);
                    } else {
                        isSalgadinho = true;
                        dto.setDespesa(s.getCusto());
                    }
                    dto
                            .setProductName(s.getProduct().getName())
                            .setTotal(s.getTotal())
                            .setReceita(s.getTotal())
                            .setLiquido(dto.getReceita().subtract(dto.getDespesa()));

                    if (mapaVendaPorProduto.containsKey(s.getProduct().getId())) {
                        SaledProductDto dtoExistent = mapaVendaPorProduto.get(s.getProduct().getId());
                        dtoExistent.setTotal(dtoExistent.getTotal().add(dto.getTotal()));
                        dtoExistent.setReceita(dtoExistent.getReceita().add(dto.getReceita()));
                        dtoExistent.setLiquido(dtoExistent.getReceita().subtract(dtoExistent.getDespesa()));
                        if(isSalgadinho){
                            dtoExistent.setDespesa(dtoExistent.getDespesa().add(dto.getDespesa()));
                        }

                        dtoExistent.setProductName(dtoExistent.getProductName());
                        mapaVendaPorProduto.put(s.getProduct().getId(), dtoExistent);
                    } else {
                        mapaVendaPorProduto.put(s.getProduct().getId(), dto);
                    }

                });
            }
            List listRetorno = new ArrayList<SaledProductDto>(mapaVendaPorProduto.values());
            Collections.sort(listRetorno);
            return listRetorno;

        } catch (Exception e) {
            throw new AppRuntimeException(AppUtil.generateRandomString());
        }
    }

}
