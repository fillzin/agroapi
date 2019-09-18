package br.com.agrostok.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.agrostok.dto.ProdutoDto;
import br.com.agrostok.dto.RetornoDto;
import br.com.agrostok.dto.filter.PaginacaoDto;
import br.com.agrostok.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<RetornoDto> create(@RequestBody(required = true) ProdutoDto produtoDto) {
		productService.createProduct(produtoDto);
		return ResponseEntity.ok(new RetornoDto("OK"));
	}

	@GetMapping(value = "/list")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<List<ProdutoDto>> list(@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer count) {
		return ResponseEntity.ok(productService.listAll(new PaginacaoDto(page, count)));
	}

	@GetMapping(value = "/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<ProdutoDto> get(@PathVariable("id") Long id) {
		return ResponseEntity.ok(productService.findDtoById(id));
	}

	@PutMapping
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<RetornoDto> updateProduct(@RequestBody(required = true) ProdutoDto produtoDto) {
		productService.updateProduct(produtoDto);
		return ResponseEntity.ok(new RetornoDto("OK"));
	}

}
