package br.com.gestaoprocesso.enums;

import java.util.stream.Stream;

public enum StockOperationEnum {
	SUBTRACT("Venda",0),
	ADD("Entrada",1);

	private int code;
	private String descricao;	

	private StockOperationEnum(String descricao, int code) {
		this.code = code;
		this.descricao = descricao;
	}

	public int getCode() {
		return code;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public static StockOperationEnum getByCode(Integer code) {
		return Stream.of(StockOperationEnum.values())
				.filter(stockEnum->stockEnum.getCode() == code)
				.findAny().get();
	}

}
