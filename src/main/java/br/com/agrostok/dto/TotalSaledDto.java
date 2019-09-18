package br.com.agrostok.dto;

import java.math.BigDecimal;

public class TotalSaledDto {

	private String month;
	private BigDecimal total;

	public TotalSaledDto() {
		super();
	}

	
	public TotalSaledDto(String month, BigDecimal total) {
		super();
		this.month = month;
		this.total = total;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}
