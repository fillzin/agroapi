package br.com.agrostok.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "product")
public class Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1554100397236384208L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	@NotNull(message = "Nome é obrigatório")
	@NotEmpty(message = "Nome é obrigatório")
	@Length(max = 45)
	private String name;

	@Column(name = "value")
	private BigDecimal value;

	@Column(name = "value_sale")
	@NotNull(message = "Valor é obrigatório")
	private BigDecimal valueSale;

	@Column(name = "value_sale2")
	private BigDecimal valueSale2;

	@Column(name = "description_value")
	@Length(max = 45)
	private String descriptionValue;

	@Column(name = "description_value_2")
	@Length(max = 45)
	private String descriptionValue2;

	@Column(name = "created_count")
	private Integer count;
	
	@Column(name = "min_stock")
	private Integer minStock;

	@Column(name = "two_price")
	private Boolean twoPrice;

	@Column(name = "tem_ingrediente")
	private Boolean temIngrediente;

	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@Column(name = "updated_date")
	private LocalDateTime updatedDate;

	@Column(name = "user_created_id")
	private Long userCreatedId;

	@Column(name = "user_updated_id")
	private Long userUpdatedId;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "product")
	private Stock stock;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ProductIngrediente> ingredientes;

	public Product() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
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

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Integer getMinStock() {
		return minStock;
	}

	public void setMinStock(Integer minStock) {
		this.minStock = minStock;
	}


	public BigDecimal getValueSale() {
		return valueSale;
	}

	public Product setValueSale(BigDecimal valueSale) {
		this.valueSale = valueSale;
		return this;
	}

	public BigDecimal getValueSale2() {
		return valueSale2;
	}

	public Product setValueSale2(BigDecimal valueSale2) {
		this.valueSale2 = valueSale2;
		return this;
	}

	public String getDescriptionValue() {
		return descriptionValue;
	}

	public Product setDescriptionValue(String descriptionValue) {
		this.descriptionValue = descriptionValue;
		return this;
	}

	public String getDescriptionValue2() {
		return descriptionValue2;
	}

	public Product setDescriptionValue2(String descriptionValue2) {
		this.descriptionValue2 = descriptionValue2;
		return this;
	}

	public Boolean getTwoPrice() {
		return twoPrice;
	}

	public Product setTwoPrice(Boolean twoPrice) {
		this.twoPrice = twoPrice;
		return this;
	}

	public Boolean getTemIngrediente() {
		return temIngrediente;
	}

	public Product setTemIngrediente(Boolean temIngrediente) {
		this.temIngrediente = temIngrediente;
		return this;
	}

	public List<ProductIngrediente> getIngredientes() {
		return ingredientes;
	}

	public Product setIngredientes(List<ProductIngrediente> ingredientes) {
		this.ingredientes = ingredientes;
		return this;
	}
}
