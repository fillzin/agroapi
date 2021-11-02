package br.com.agrostok.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "stock")
public class Stock implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1554100397236384208L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "product_id", referencedColumnName = "id", nullable = true)
	private Product product;

	@ManyToOne
	@JoinColumn(name = "ingrediente_id", referencedColumnName = "id", nullable = true)
	private Ingrediente ingrediente;


	@Column(name = "count")
	private Integer count;

	@Column(name = "value")
	private BigDecimal value;

	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@Column(name = "updated_date")
	private LocalDateTime updatedDate;

	@Column(name = "user_created_id")
	private Long userCreatedId;

	@Column(name = "user_updated_id")
	private Long userUpdatedId;

	public Stock() {
	}

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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUserCreatedId() {
		return userCreatedId;
	}

	public void setUserCreatedId(Long userCreatedId) {
		this.userCreatedId = userCreatedId;
	}

	public Long getUserUpdatedId() {
		return userUpdatedId;
	}

	public void setUserUpdatedId(Long userUpdatedId) {
		this.userUpdatedId = userUpdatedId;
	}

	public Ingrediente getIngrediente() {
		return ingrediente;
	}

	public Stock setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
		return this;
	}

	public BigDecimal getValue() {
		return value;
	}

	public Stock setValue(BigDecimal value) {
		this.value = value;
		return this;
	}

	@PrePersist
	public void initDate(){
		this.createdDate = LocalDateTime.now();
	}
}
