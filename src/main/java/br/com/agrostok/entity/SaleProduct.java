package br.com.agrostok.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sale_product")
public class SaleProduct implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1554100397236384208L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
	private Product product;

	@ManyToOne
	@JoinColumn(name = "stock_id", referencedColumnName = "id", nullable = false)
	private Stock stock;

	@ManyToOne
	@JoinColumn(name = "sale_id", referencedColumnName = "id", nullable = false)
	private Sale sale;

	@Column(name = "count")
	private Integer count;

	@Column(name = "total")
	private BigDecimal total;

	@Column(name = "user_created_id")
	private Long userCreatedId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Long getUserCreatedId() {
		return userCreatedId;
	}

	public void setUserCreatedId(Long userCreatedId) {
		this.userCreatedId = userCreatedId;
	}

}
