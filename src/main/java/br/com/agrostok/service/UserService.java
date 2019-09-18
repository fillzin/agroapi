package br.com.agrostok.service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.agrostok.dto.UserDto;
import br.com.agrostok.entity.User;
import br.com.agrostok.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository; 
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
    private NotificationService notificationService;
	
	public void createUser(UserDto userDto) {
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		User user = convertToEntity(userDto);
		
		Set<ConstraintViolation<User>> violations = validator.validate(user);

		if(Objects.nonNull(violations) && !violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}
		
		userRepository.save(user);
		
		notificationService.createNotification(user, "Obrigado por se cadastrar no AgroSTOK.");
	}

	private User convertToEntity(UserDto userDto) {
		User usuario = modelMapper.map(userDto, User.class);
		usuario.setUsername(userDto.getEmail());
		usuario.setCreatedDate(LocalDateTime.now());
		return usuario;
	}

	public UserDto findUserByLoginAndPassword(String email, String password) {
		Optional<User> user = userRepository.findByEmailAndPassword(email, password);
		if(user.isPresent()) {
			return modelMapper.map(user.get(), UserDto.class);
		}
		return null;
	}
	
	public User getLoggerUser() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	public void updateUser(UserDto userDto) {
		Optional<User> user = userRepository.findById(userDto.getId());
		if (user.isPresent()) {
			user.get().setNome(userDto.getNome());
//			user.get().setEmail(userDto.getEmail());
			userRepository.save(user.get());
		}
		
	}
}
