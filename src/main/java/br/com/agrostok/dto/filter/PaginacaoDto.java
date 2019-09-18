package br.com.agrostok.dto.filter;

public class PaginacaoDto {

	private Integer pagina;
	private Integer qtdRegistros;

	public PaginacaoDto() {
		super();
	}

	public PaginacaoDto(Integer pagina, Integer qtdRegistros) {
		super();
		this.pagina = pagina;
		this.qtdRegistros = qtdRegistros;
	}

	public Integer getPagina() {
		return pagina;
	}

	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}

	public Integer getQtdRegistros() {
		return qtdRegistros;
	}

	public void setQtdRegistros(Integer qtdRegistros) {
		this.qtdRegistros = qtdRegistros;
	}

}
