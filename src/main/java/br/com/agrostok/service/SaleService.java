package br.com.agrostok.service;

import static java.time.Month.DECEMBER;
import static java.time.Month.JANUARY;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.agrostok.dto.PersonDto;
import br.com.agrostok.dto.ProductDto;
import br.com.agrostok.dto.ReturnSaleDto;
import br.com.agrostok.dto.SaleDto;
import br.com.agrostok.dto.filter.PaginacaoDto;
import br.com.agrostok.entity.Product;
import br.com.agrostok.entity.Sale;
import br.com.agrostok.entity.SaleProduct;
import br.com.agrostok.exception.AppRuntimeException;
import br.com.agrostok.repository.SaleProductRepository;
import br.com.agrostok.repository.SaleRepository;
import br.com.agrostok.util.AppUtil;
import br.com.gestaoprocesso.enums.PaginacaoEnum;

@Service
public class SaleService {

//	private static final Logger logger = LogManager.getLogger(ProductService.class);

	@Autowired
	private SaleRepository saleRepository;

	@Autowired
	private SaleProductRepository saleProductRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	@Autowired
	private StockService stockService;

	@Autowired
	private ModelMapper modelMapper;

	@Transactional
	public void sale(SaleDto saleDto) {

		Sale sale = new Sale();

		BigDecimal totalValue = BigDecimal.ZERO;
		List<SaleProduct> salesProducts = new ArrayList<>();
		for (ProductDto productDto : saleDto.getProducts()) {

//            Optional<Stock> stock = stockService.findByProductAndUser(userService.getLoggerUser().getId(),
//                    productDto.getProductId());
//            if (!stock.isPresent()) {
//                throw new AppBusinessException(AppUtil.generateRandomString(), "Estoque não encontrado para esse produto.");
//            }
//            if (stock.get().getCount() < productDto.getCount()) {
//                throw new AppBusinessException(AppUtil.generateRandomString(),
//                        "Quantidade atual do estoque é: " + stock.get().getCount());
//            }

			Product product = productService.findById(productDto.getProductId());
//				BigDecimal productTotalValue = product.getValue().multiply(new BigDecimal(productDto.getCount()));

			SaleProduct saleProduct = new SaleProduct();
			saleProduct.setCount(productDto.getCount());
			saleProduct.setProduct(product);
//            saleProduct.setStock(stock.get());
			saleProduct.setTotal(productDto.getSaleTotalValue());
			saleProduct.setUserCreatedId(userService.getLoggerUser().getId());
			saleProduct.setSale(sale);
			if (!product.getTemIngrediente() && Objects.nonNull(product.getValue())) {
				saleProduct.setCusto(product.getValue().multiply(new BigDecimal(productDto.getCount())));
			}
			salesProducts.add(saleProduct);
			totalValue = totalValue.add(productDto.getSaleTotalValue());
//            stockService.subtractFromStock(product, productDto.getCount());
		}

		sale.setSaleProducts(salesProducts);
		sale.setValue(totalValue);
		sale.setUserCreatedId(userService.getLoggerUser().getId());
		sale.setCreatedDate(LocalDateTime.now());
		sale.setBloco(saleDto.getBloco());
		sale.setCondominio(saleDto.getCondominio());
		sale.setCasa(saleDto.getCasa());
		saleRepository.save(sale);
	}

	@Transactional
	public List<ReturnSaleDto> listAll(PaginacaoDto paginacaoDto) {
		try {
			PageRequest paginacao = PageRequest.of(PaginacaoEnum.getPage(paginacaoDto.getPagina()),
					PaginacaoEnum.getTotalRegistros(paginacaoDto.getQtdRegistros()));

			Page<Sale> pageSales = saleRepository.findByFiltros(userService.getLoggerUser().getId(), paginacao);
			if (!pageSales.getContent().isEmpty()) {
				
				return pageSales.getContent().stream().map(sale -> {
					
					return convertSaleDto(sale);
				}).collect(Collectors.toList());
			}
		} catch (Exception e) {
			throw new AppRuntimeException(AppUtil.generateRandomString());
		}

		return new ArrayList<ReturnSaleDto>();
	}

	public List<Sale> getAllSaleByActualYear() {
		int actualYear = LocalDate.now().getYear();
		LocalDateTime dataInicio = LocalDateTime.of(actualYear, JANUARY, 1, 1, 0);
		LocalDateTime dataFim = LocalDateTime.of(actualYear, DECEMBER, 31, 1, 0);
		return saleRepository.findByYear(userService.getLoggerUser().getId(), dataInicio, dataFim);
	}

	private ReturnSaleDto convertSaleDto(Sale sale) {
		ReturnSaleDto saleDto = new ReturnSaleDto();
		saleDto.setCreatedDate(sale.getCreatedDate());

		List<SaleProduct> products = saleProductRepository.findBySaleId(sale.getId());
		
		String nameProducts = products.stream().map(saleProduct -> saleProduct.getProduct().getName())
				.collect(Collectors.joining(","));

		saleDto.setProductName(nameProducts);
		saleDto.setTotal(sale.getValue());
		return saleDto;
	}

	public List<SaleDto> orderByClient(PaginacaoDto paginacaoDto) {
		try {
			PageRequest paginacao = PageRequest.of(PaginacaoEnum.getPage(paginacaoDto.getPagina()),
					PaginacaoEnum.getTotalRegistros(paginacaoDto.getQtdRegistros()));
			
			Page<SaleDto> pageProducts = saleRepository.orderByClient(paginacao);
			if(!pageProducts.getContent().isEmpty()) {
				return pageProducts.getContent();
			}
			

			
		} catch (Exception e) {
			System.out.println(e);
			throw new AppRuntimeException(AppUtil.generateRandomString());
		}
		return new ArrayList<SaleDto>();

	}
	
	public List<ReturnSaleDto> orderedByHighestSell() {
		try {
			
			
			List<ReturnSaleDto> lista = saleRepository.findProductsByNameAndHighestValue();
			
			/* Tu fez certinho aqui */
		
			if(!lista.isEmpty()) {
				

				 lista.get(0).setMsg("Esse >>>>> é o produto mais comprado");
					
				
				return lista;
			}

			
		} catch (Exception e) {
			System.out.println(e);
			throw new AppRuntimeException(AppUtil.generateRandomString());
		}
		return new ArrayList<ReturnSaleDto>();

	}

	
}
