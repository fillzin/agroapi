package br.com.agrostok.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ProdutoDto {
	private Long id;
	private String name;
	private BigDecimal value;
	private BigDecimal valueSale;
	private String descriptionValue;
	private String descriptionValue2;
	private BigDecimal valueSale2;
	private int count;
	private int estoque;
	private Integer minStock;
	private boolean twoPrice;
	private boolean temIngrediente;
	private List<IngredienteDto> ingredientes;
	private LocalDateTime date;
	private BigDecimal valueToSum;

	public Long getId() {
		return id;
	}

	public ProdutoDto setId(Long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public ProdutoDto setName(String name) {
		this.name = name;
		return this;
	}

	public BigDecimal getValue() {
		return value;
	}

	public ProdutoDto setValue(BigDecimal value) {
		this.value = value;
		return this;
	}

	public BigDecimal getValueSale() {
		return valueSale;
	}

	public ProdutoDto setValueSale(BigDecimal valueSale) {
		this.valueSale = valueSale;
		return this;
	}

	public String getDescriptionValue() {
		return descriptionValue;
	}

	public ProdutoDto setDescriptionValue(String descriptionValue) {
		this.descriptionValue = descriptionValue;
		return this;
	}

	public String getDescriptionValue2() {
		return descriptionValue2;
	}

	public ProdutoDto setDescriptionValue2(String descriptionValue2) {
		this.descriptionValue2 = descriptionValue2;
		return this;
	}

	public BigDecimal getValueSale2() {
		return valueSale2;
	}

	public ProdutoDto setValueSale2(BigDecimal valueSale2) {
		this.valueSale2 = valueSale2;
		return this;
	}

	public int getCount() {
		return count;
	}

	public ProdutoDto setCount(int count) {
		this.count = count;
		return this;
	}

	public int getEstoque() {
		return estoque;
	}

	public ProdutoDto setEstoque(int estoque) {
		this.estoque = estoque;
		return this;
	}

	public Integer getMinStock() {
		return minStock;
	}

	public ProdutoDto setMinStock(Integer minStock) {
		this.minStock = minStock;
		return this;
	}

	public boolean isTwoPrice() {
		return twoPrice;
	}

	public ProdutoDto setTwoPrice(boolean twoPrice) {
		this.twoPrice = twoPrice;
		return this;
	}

	public boolean isTemIngrediente() {
		return temIngrediente;
	}

	public ProdutoDto setTemIngrediente(boolean temIngrediente) {
		this.temIngrediente = temIngrediente;
		return this;
	}

	public List<IngredienteDto> getIngredientes() {
		return ingredientes;
	}

	public ProdutoDto setIngredientes(List<IngredienteDto> ingredientes) {
		this.ingredientes = ingredientes;
		return this;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public ProdutoDto setDate(LocalDateTime date) {
		this.date = date;
		return this;
	}

	public BigDecimal getValueToSum() {
		return valueToSum;
	}

	public ProdutoDto setValueToSum(BigDecimal valueToSum) {
		this.valueToSum = valueToSum;
		return this;
	}
}
