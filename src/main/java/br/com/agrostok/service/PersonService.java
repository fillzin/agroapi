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

import br.com.agrostok.dto.PersonDto;
import br.com.agrostok.dto.ProdutoDto;
import br.com.agrostok.dto.ValidationDto;
import br.com.agrostok.dto.filter.PaginacaoDto;
import br.com.agrostok.entity.Person;
import br.com.agrostok.entity.Product;
import br.com.agrostok.exception.AppRuntimeException;
import br.com.agrostok.exception.ValidationException;
import br.com.agrostok.repository.PersonRepository;
import br.com.agrostok.util.AppUtil;
import br.com.gestaoprocesso.enums.PaginacaoEnum;
import br.com.gestaoprocesso.enums.TypePersonEnum;

@Service
public class PersonService {

//	private static final Logger logger = LogManager.getLogger(ProductService.class);

	@Autowired
	private PersonRepository clientRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	public void createClient(PersonDto clientDto) {
		validateIfClientAlreadyExistsByName(clientDto);

		try {

			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();
			Person client = convertToEntity(clientDto);

			Set<ConstraintViolation<Person>> violations = validator.validate(client);

			if (Objects.nonNull(violations) && !violations.isEmpty()) {
				throw new ConstraintViolationException(violations);
			}

			clientRepository.save(client);
		} catch (Exception e) {
			throw new AppRuntimeException(AppUtil.generateRandomString());
		}

	}

	public void updateClient(PersonDto clientDto) {
		validateIfClientAlreadyExistsByName(clientDto);
		try {
			Person clientToUpdate = findById(clientDto.getId());
			
			clientToUpdate.setName(clientDto.getName());
			clientToUpdate.setEmail(clientDto.getEmail());
			clientToUpdate.setPhone(clientDto.getPhone());
			clientToUpdate.setPhone2(clientDto.getPhone2());
			
			clientRepository.save(clientToUpdate);
		} catch (Exception e) {
			throw new AppRuntimeException(AppUtil.generateRandomString());
		}

	}

	public List<PersonDto> listAll(PaginacaoDto paginacaoDto, TypePersonEnum clientEnum) {
		try {
			PageRequest paginacao = PageRequest.of(PaginacaoEnum.getPage(paginacaoDto.getPagina()),
					PaginacaoEnum.getTotalRegistros(paginacaoDto.getQtdRegistros()));

			Page<Person> pageClients = clientRepository.findByFiltros(userService.getLoggerUser().getId(), clientEnum.getCode(), paginacao);
			if (!pageClients.getContent().isEmpty()) {
				return pageClients.getContent().stream().map(client -> modelMapper.map(client, PersonDto.class))
						.collect(Collectors.toList());
			}
		} catch (Exception e) {
			throw new AppRuntimeException(AppUtil.generateRandomString());
		}

		return new ArrayList<PersonDto>();
	}

	public PersonDto findDtoById(Long id) {
		try {
			Optional<Person> client = clientRepository.findById(id);
			if (client.isPresent()) {
				return modelMapper.map(client.get(), PersonDto.class);
			}

		} catch (Exception e) {
			throw new AppRuntimeException(AppUtil.generateRandomString());
		}
		return null;
	}

	public Person findById(Long id) {
		try {
			Optional<Person> client = clientRepository.findById(id);
			if (client.isPresent()) {
				return client.get();
			}

		} catch (Exception e) {
			throw new AppRuntimeException(AppUtil.generateRandomString());
		}
		return null;
	}

	private void validateIfClientAlreadyExistsByName(PersonDto clientDto) {
		Optional<Person> clientFound = clientRepository.findByNameAndUserCreatedId(clientDto.getName().trim(),
				userService.getLoggerUser().getId());
		if (clientFound.isPresent() && !clientFound.get().getId().equals(clientDto.getId())) {
			ValidationDto validationDto = new ValidationDto();
			validationDto.setMensagem("Nome de cliente já existente.");
			throw new ValidationException(Arrays.asList(validationDto));
		}
	}

	private Person convertToEntity(PersonDto clientDto) {

		Person client = modelMapper.map(clientDto, Person.class);
		client.setCreatedDate(LocalDateTime.now());
		client.setUserCreatedId(Long.valueOf(userService.getLoggerUser().getId()));

		return client;
	}

}
