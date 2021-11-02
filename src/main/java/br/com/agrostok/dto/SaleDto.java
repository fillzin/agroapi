package br.com.agrostok.dto;

import java.math.BigDecimal;
import java.util.List;

public class SaleDto {

	private List<ProductDto> products;
	private String condominio;
	private String bloco;
	private String casa;
	private IngredienteDto ingredienteDto;
	private BigDecimal value;
	private Integer count;

	public List<ProductDto> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDto> products) {
		this.products = products;
	}

	public String getCondominio() {
		return condominio;
	}

	public SaleDto setCondominio(String condominio) {
		this.condominio = condominio;
		return this;
	}

	public String getBloco() {
		return bloco;
	}

	public SaleDto setBloco(String bloco) {
		this.bloco = bloco;
		return this;
	}

	public String getCasa() {
		return casa;
	}

	public SaleDto setCasa(String casa) {
		this.casa = casa;
		return this;
	}

	public IngredienteDto getIngredienteDto() {
		return ingredienteDto;
	}

	public SaleDto setIngredienteDto(IngredienteDto ingredienteDto) {
		this.ingredienteDto = ingredienteDto;
		return this;
	}

	public BigDecimal getValue() {
		return value;
	}

	public SaleDto setValue(BigDecimal value) {
		this.value = value;
		return this;
	}

	public Integer getCount() {
		return count;
	}

	public SaleDto setCount(Integer count) {
		this.count = count;
		return this;
	}
}
