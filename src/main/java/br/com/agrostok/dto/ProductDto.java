package br.com.agrostok.dto;

import java.math.BigDecimal;

public class ProductDto {

	private Long productId;
	private Integer count;
	private BigDecimal saleTotalValue;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public BigDecimal getSaleTotalValue() {
		return saleTotalValue;
	}

	public ProductDto setSaleTotalValue(BigDecimal saleTotalValue) {
		this.saleTotalValue = saleTotalValue;
		return this;
	}
}
