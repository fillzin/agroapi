package br.com.agrostok.dto;

import java.math.BigDecimal;

public class IngredienteDto {
	public IngredienteDto(Long id, String name, BigDecimal value) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
	}

	public IngredienteDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	private Long id;
	private String name;
	private BigDecimal value;
	private Integer quantidade;

	public Long getId() {
		return id;
	}

	public IngredienteDto setId(Long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public IngredienteDto setName(String name) {
		this.name = name;
		return this;
	}

	public BigDecimal getValue() {
		return value;
	}

	public IngredienteDto setValue(BigDecimal value) {
		this.value = value;
		return this;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public IngredienteDto setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
		return this;
	}
}
