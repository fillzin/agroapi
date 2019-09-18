package br.com.agrostok.erro;

public class Erro {

	private String campo;
	private String mensagem;

	public enum TipoErro {
		ERRO, ALERTA;
	}

	public Erro() {
		super();
	}

	public Erro(String campo, String mensagem) {
		super();
		this.campo = campo;
		this.mensagem = mensagem;
	}

	public String getCampo() {
		return campo;
	}

	public String getMensagem() {
		return mensagem;
	}

}
