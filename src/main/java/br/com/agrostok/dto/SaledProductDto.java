package br.com.agrostok.dto;

import java.math.BigDecimal;

public class SaledProductDto implements Comparable<SaledProductDto>{

	private String productName;
	private BigDecimal total;
	private BigDecimal receita;
	private BigDecimal despesa;
	private BigDecimal liquido;
	private Long productId;

	public SaledProductDto(BigDecimal total, String productName, BigDecimal custo, Long productId) {
		super();
		this.productName = productName;
		this.receita = total;
		this.total = total;
		this.despesa = custo;
		this.productId = productId;
		this.liquido = this.receita.subtract(this.despesa);
	}


	public SaledProductDto() {
		super();
	}

	public String getProductName() {
		return productName;
	}

	public SaledProductDto setProductName(String productName) {
		this.productName = productName;
		return this;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public SaledProductDto setTotal(BigDecimal total) {
		this.total = total;
		return this;
	}

	public BigDecimal getReceita() {
		return receita;
	}

	public SaledProductDto setReceita(BigDecimal receita) {
		this.receita = receita;
		return this;
	}

	public BigDecimal getDespesa() {
		return despesa;
	}

	public SaledProductDto setDespesa(BigDecimal despesa) {
		this.despesa = despesa;
		return this;
	}

	public BigDecimal getLiquido() {
		return liquido;
	}

	public SaledProductDto setLiquido(BigDecimal liquido) {
		this.liquido = liquido;
		return this;
	}

	public Long getProductId() {
		return productId;
	}

	public SaledProductDto setProductId(Long productId) {
		this.productId = productId;
		return this;
	}

	@Override
	public int compareTo(SaledProductDto e) {
		return e.getReceita().compareTo(this.getReceita());
	}
}
