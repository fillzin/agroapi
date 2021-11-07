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
import br.com.agrostok.entity.Product;
import br.com.agrostok.repository.ProductRepository;
import br.com.agrostok.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductRepository productRepository;

	@PostMapping
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<RetornoDto> create(@RequestBody(required = true) ProdutoDto produtoDto) {
		productService.createProduct(produtoDto);
		return ResponseEntity.ok(new RetornoDto("OK"));
	}

	@GetMapping(value = "/list")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<List<ProdutoDto>> list(@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer count, @RequestParam(required = false) String name) {
		return ResponseEntity.ok(productService.listAll(new PaginacaoDto(page, count), name));
	}
	


	@GetMapping(value = "/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<ProdutoDto> get(@PathVariable("id") Long id) {
		return ResponseEntity.ok(productService.findDtoById(id));
	}
	
	@GetMapping(value = "/julio/{name}")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<List<ProdutoDto>> findByName(@PathVariable("name") String name,
			@RequestParam("pagina") Integer pagina, 
			@RequestParam("quantidadeRegistros") Integer quantidadeRegistros) {
		return ResponseEntity.ok(productService.findByName(name, pagina, quantidadeRegistros));
	}
	


	@PutMapping
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<RetornoDto> updateProduct(@RequestBody(required = true) ProdutoDto produtoDto) {
		productService.updateProduct(produtoDto);
		return ResponseEntity.ok(new RetornoDto("OK"));
	}

}
