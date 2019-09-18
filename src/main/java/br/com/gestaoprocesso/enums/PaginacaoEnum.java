package br.com.gestaoprocesso.enums;

import java.util.Optional;

public enum PaginacaoEnum {

	PAGINA(0), 
	TOTAL_REGISTROS(5);

	private int valor;

	private PaginacaoEnum(int valor) {
		this.valor = valor;
	}

	public int getValor() {
		return valor;
	}

	public static int getPage(Integer paginaParam) {
		return Optional.ofNullable(paginaParam).orElse(PaginacaoEnum.PAGINA.getValor());
	}
	
	public static int getTotalRegistros(Integer qtdTotalRegistros) {
		return Optional.ofNullable(qtdTotalRegistros).orElse(PaginacaoEnum.TOTAL_REGISTROS.getValor());
	}
	
}
