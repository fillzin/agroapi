package br.com.agrostok.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReturnSaleDto {
	
	private Long id;
	private String productName;
	private LocalDateTime createdDate;
	private BigDecimal total;
	private BigDecimal value;
	private Long soma;
	private String msg;


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

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}



	public ReturnSaleDto(Long id, String productName, BigDecimal value, Long soma) {
		super();
		this.setId(id);
		this.productName = productName;
		this.value = value;
		this.soma = soma;

	}

	public ReturnSaleDto() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSoma() {
		return soma;
	}

	public void setSoma(Long soma) {
		this.soma = soma;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
