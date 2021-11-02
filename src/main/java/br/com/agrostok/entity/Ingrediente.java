package br.com.agrostok.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ingrediente")
public class Ingrediente implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1554100397226384208L;

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

	@Column(name = "quantidade")
	private Integer quantidade;

	@OneToMany(mappedBy = "ingrediente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Stock> stocks = new ArrayList<>();


	public Long getId() {
		return id;
	}

	public Ingrediente setId(Long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Ingrediente setName(String name) {
		this.name = name;
		return this;
	}

	public BigDecimal getValue() {
		return value;
	}

	public Ingrediente setValue(BigDecimal value) {
		this.value = value;
		return this;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public Ingrediente setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
		return this;
	}

	public List<Stock> getStocks() {
		return stocks;
	}

	public Ingrediente setStocks(List<Stock> stocks) {
		this.stocks = stocks;
		return this;
	}
}
