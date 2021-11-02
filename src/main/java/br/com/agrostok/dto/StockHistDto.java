package br.com.agrostok.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StockHistDto {

	private String productName;
	private LocalDateTime createdDate;
	private Integer count;
	private String operation;
	private BigDecimal value;

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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public BigDecimal getValue() {
		return value;
	}

	public StockHistDto setValue(BigDecimal value) {
		this.value = value;
		return this;
	}
}
