package br.com.gestaoprocesso.enums;

public enum TypePersonEnum {

	CLIENT(0), PROVIDER(1);

	private int code;

	private TypePersonEnum(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
