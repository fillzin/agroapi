package br.com.agrostok.exception;

import java.util.List;

import br.com.agrostok.dto.ValidationDto;

public class ValidationException extends RuntimeException {

	private List<ValidationDto> validation;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ValidationException(List<ValidationDto> validation) {
		this.validation = validation;
	}

	public List<ValidationDto> getValidator() {
		return validation;
	}

}
