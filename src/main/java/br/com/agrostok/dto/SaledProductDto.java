package br.com.agrostok.dto;

import java.math.BigDecimal;

public class SaledProductDto {

	private String productName;
	private BigDecimal total;

	public SaledProductDto(BigDecimal total, String productName) {
		super();
		this.productName = productName;
		this.total = total;
	}

	public SaledProductDto() {
		super();
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}
