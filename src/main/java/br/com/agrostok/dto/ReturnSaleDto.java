package br.com.agrostok.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReturnSaleDto {

	private String productName;
	private LocalDateTime createdDate;
	private BigDecimal total;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}
