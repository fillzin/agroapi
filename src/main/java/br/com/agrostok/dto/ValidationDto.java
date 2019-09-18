package br.com.agrostok.dto;

import java.util.ArrayList;
import java.util.List;

public class ValidationDto {

	private String localErro;
	private String erro;
	private String mensagem;
	private List<Integer> args;

	
	
	public ValidationDto() {
		super();
	}

	public ValidationDto(String localErro, String erro, String mensagem, List<Integer> args) {
		super();
		this.localErro = localErro;
		this.erro = erro;
		this.mensagem = mensagem;
		this.args = args;
	}
	public ValidationDto(String localErro, String erro, String mensagem) {
		super();
		this.localErro = localErro;
		this.erro = erro;
		this.mensagem = mensagem;
		this.args = new ArrayList<>();
	}

	public String getLocalErro() {
		return localErro;
	}

	public void setLocalErro(String localErro) {
		this.localErro = localErro;
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public List<Integer> getArgs() {
		return args;
	}

	public void setArgs(List<Integer> args) {
		this.args = args;
	}

}
