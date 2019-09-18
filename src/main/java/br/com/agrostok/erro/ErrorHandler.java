package br.com.agrostok.erro;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.agrostok.dto.ValidationDto;
import br.com.agrostok.exception.AppBusinessException;
import br.com.agrostok.exception.AppRuntimeException;
import br.com.agrostok.exception.ValidationException;

@ControllerAdvice
public class ErrorHandler {

	private static final Logger logger = LogManager.getLogger(ErrorHandler.class);

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseBody
	public ResponseEntity<?> processValidationError(ConstraintViolationException ex) {

		List<ValidationDto> erros = ex.getConstraintViolations().stream().map(violation -> {
			ValidationDto validatorDto = new ValidationDto();

			validatorDto.setMensagem(violation.getMessage());
			validatorDto.setArgs(Arrays.asList((Integer) getAttribute(violation, "max")));
			validatorDto.setErro((String) getAttribute(violation, "erro"));
			validatorDto.setLocalErro((String) getAttribute(violation, "localErro"));

			return validatorDto;
		}).collect(Collectors.toList());

		logger.info("Erro de validação de campos.");
		return new ResponseEntity<>(erros, HttpStatus.PRECONDITION_FAILED);
	}

	@ExceptionHandler(ValidationException.class)
	@ResponseBody
	public ResponseEntity<?> processValidationError(ValidationException ex) {

		logger.info("Erro de validação de campos.");
		return new ResponseEntity<>(ex.getValidator(), HttpStatus.PRECONDITION_FAILED);
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<Erro> erro(Exception e) {
		logger.error("Erro inesperado." + e.getMessage());
		return new ResponseEntity<Erro>(new Erro(null, "Erro interno do Sistema"), INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(AppRuntimeException.class)
	@ResponseBody
	public ResponseEntity<Erro> erro(AppRuntimeException e) {
		logger.error("Erro inesperado. [" + e.getErrorCode() + "]", e);
		return new ResponseEntity<Erro>(
				new Erro(e.getErrorCode(), "Erro interno do Sistema, código: [" + e.getErrorCode() + "]"),
				INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(AppBusinessException.class)
	@ResponseBody
	public ResponseEntity<Erro> erro(AppBusinessException e) {
		logger.error("Erro inesperado. [" + e.getErrorCode() + "]", e);
		return new ResponseEntity<Erro>(
				new Erro(e.getErrorCode(), e.getMessage()),
				HttpStatus.BAD_REQUEST);
	}

	private Object getAttribute(ConstraintViolation<?> violation, String atributo) {
		return violation.getConstraintDescriptor().getAttributes().get(atributo);
	}
}
