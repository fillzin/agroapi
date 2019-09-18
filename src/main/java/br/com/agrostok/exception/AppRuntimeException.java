package br.com.agrostok.exception;

public class AppRuntimeException extends RuntimeException {

	private String errorCode;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AppRuntimeException(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

}
