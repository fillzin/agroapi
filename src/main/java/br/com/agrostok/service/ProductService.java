package br.com.agrostok.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

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
import br.com.agrostok.entity.Product;
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

			productRepository.save(product);
			stockService.createStock(product);
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
			productToUpdate.setMinStock(produtoDto.getMinStock());
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
					produtoDto = modelMapper.map(product, ProdutoDto.class);
					produtoDto.setEstoque(product.getStock().getCount());
					return produtoDto;

				}).collect(Collectors.toList());
			}
		} catch (Exception e) {
			throw new AppRuntimeException(AppUtil.generateRandomString());
		}

		return new ArrayList<ProdutoDto>();
	}

	public ProdutoDto findDtoById(Long id) {
		try {
			Optional<Product> product = productRepository.findById(id);
			if (product.isPresent()) {
				return modelMapper.map(product.get(), ProdutoDto.class);
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
		product.setCreatedDate(LocalDateTime.now());
		product.setUserCreatedId(Long.valueOf(userService.getLoggerUser().getId()));

		return product;
	}

	public List<SaledProductDto> findProductsWithTotalSaled(){
		try {
			
			return productRepository.findProductAndSaleValue(userService.getLoggerUser().getId());
		} catch (Exception e) {
			throw new AppRuntimeException(AppUtil.generateRandomString());
		}
	}

}
