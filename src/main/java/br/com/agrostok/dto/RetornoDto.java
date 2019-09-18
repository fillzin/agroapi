package br.com.agrostok.dto;

public class RetornoDto {

	private String mensagem;

	public RetornoDto() {
		super();
	}
	
	public RetornoDto(String mensagem) {
		super();
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
