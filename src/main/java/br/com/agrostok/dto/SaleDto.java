package br.com.agrostok.dto;

import java.util.List;

public class SaleDto {

	private List<ProductDto> products;
	
	public List<ProductDto> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDto> products) {
		this.products = products;
	}

}
