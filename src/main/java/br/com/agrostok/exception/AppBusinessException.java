package br.com.agrostok.exception;

public class AppBusinessException extends RuntimeException {

	private String errorCode;
	private String message;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AppBusinessException(String errorCode) {
		this.errorCode = errorCode;
	}

	public AppBusinessException(String errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getMessage() {
		return message;
	}

}
