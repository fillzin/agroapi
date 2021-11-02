package br.com.agrostok.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_ingredientes")
public class ProductIngrediente implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1554102397236384208L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "ingrediente_id",referencedColumnName = "id")
	private Ingrediente ingrediente;

	@ManyToOne
	@JoinColumn(name = "product_id",referencedColumnName = "id")
	private Product product;

	public Long getId() {
		return id;
	}

	public ProductIngrediente setId(Long id) {
		this.id = id;
		return this;
	}

	public Ingrediente getIngrediente() {
		return ingrediente;
	}

	public ProductIngrediente setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
		return this;
	}

	public Product getProduct() {
		return product;
	}

	public ProductIngrediente setProduct(Product product) {
		this.product = product;
		return this;
	}
}
