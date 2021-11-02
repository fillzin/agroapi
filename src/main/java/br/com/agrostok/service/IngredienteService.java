package br.com.agrostok.service;

import br.com.agrostok.dto.IngredienteDto;
import br.com.agrostok.dto.ProdutoDto;
import br.com.agrostok.dto.SaledProductDto;
import br.com.agrostok.dto.ValidationDto;
import br.com.agrostok.dto.filter.PaginacaoDto;
import br.com.agrostok.entity.Ingrediente;
import br.com.agrostok.entity.Product;
import br.com.agrostok.exception.AppRuntimeException;
import br.com.agrostok.exception.ValidationException;
import br.com.agrostok.repository.IngredienteRepository;
import br.com.agrostok.util.AppUtil;
import br.com.gestaoprocesso.enums.PaginacaoEnum;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IngredienteService {

//	private static final Logger logger = LogManager.getLogger(ProductService.class);

	@Autowired
	private IngredienteRepository ingredienteRepository;

	@Autowired
	private UserService userService;
	
	@Autowired
	private StockService stockService;

	@Autowired
	private ModelMapper modelMapper;

	@Transactional
	public void createIngrediente(IngredienteDto ingredienteDto) {

		try {

			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();
			Ingrediente ingrediente = convertToEntity(ingredienteDto);

			Set<ConstraintViolation<Ingrediente>> violations = validator.validate(ingrediente);

			if (Objects.nonNull(violations) && !violations.isEmpty()) {
				throw new ConstraintViolationException(violations);
			}

			ingredienteRepository.save(ingrediente);
//			stockService.createStock(ingrediente);
		} catch (Exception e) {
			throw new AppRuntimeException(AppUtil.generateRandomString());
		}

	}

	public void updateIngrediente(IngredienteDto ingredienteDto) {
		try {
			Ingrediente ingredienteToUpdate = findById(ingredienteDto.getId());
			ingredienteToUpdate.setName(ingredienteDto.getName());
			ingredienteToUpdate.setValue(ingredienteDto.getValue());
			ingredienteRepository.save(ingredienteToUpdate);
		} catch (Exception e) {
			throw new AppRuntimeException(AppUtil.generateRandomString());
		}

	}

	public List<IngredienteDto> listAll(PaginacaoDto paginacaoDto) {
		try {
			PageRequest paginacao = PageRequest.of(PaginacaoEnum.getPage(paginacaoDto.getPagina()),
					PaginacaoEnum.getTotalRegistros(paginacaoDto.getQtdRegistros()));

			Page<Ingrediente> pageProducts = ingredienteRepository.findAll(paginacao);
			if (!pageProducts.getContent().isEmpty()) {
				return pageProducts.getContent().stream().map(ingrediente -> {
					IngredienteDto ingredienteDto = new IngredienteDto();
					ingredienteDto = modelMapper.map(ingrediente, IngredienteDto.class);
					return ingredienteDto;

				}).collect(Collectors.toList());
			}
		} catch (Exception e) {
			throw new AppRuntimeException(AppUtil.generateRandomString());
		}

		return new ArrayList<IngredienteDto>();
	}

	public IngredienteDto findDtoById(Long id) {
		try {
			Optional<Ingrediente> product = ingredienteRepository.findById(id);
			if (product.isPresent()) {
				return modelMapper.map(product.get(), IngredienteDto.class);
			}

		} catch (Exception e) {
			throw new AppRuntimeException(AppUtil.generateRandomString());
		}
		return null;
	}

	public Ingrediente findById(Long id) {
		try {
			Optional<Ingrediente> product = ingredienteRepository.findById(id);
			if (product.isPresent()) {
				return product.get();
			}

		} catch (Exception e) {
			throw new AppRuntimeException(AppUtil.generateRandomString());
		}
		return null;
	}

	private void validateIfProductAlreadyExists(IngredienteDto produtoDto) {
		Optional<Ingrediente> productFound = ingredienteRepository.findByName(produtoDto.getName().trim());
		if (productFound.isPresent() && !productFound.get().getId().equals(produtoDto.getId())) {
			ValidationDto validationDto = new ValidationDto();
			validationDto.setMensagem("Nome de ingrediente já existente.");
			throw new ValidationException(Arrays.asList(validationDto));
		}
	}

	private Ingrediente convertToEntity(IngredienteDto ingredienteDto) {

		Ingrediente ingrediente = modelMapper.map(ingredienteDto, Ingrediente.class);

		return ingrediente;
	}


}
