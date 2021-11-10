package br.com.agrostok.controller;

import br.com.agrostok.dto.IngredienteDto;
import br.com.agrostok.dto.ProdutoDto;
import br.com.agrostok.dto.RetornoDto;
import br.com.agrostok.dto.filter.PaginacaoDto;
import br.com.agrostok.service.IngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingrediente")
public class IngredienteController {

	@Autowired
	private IngredienteService ingredienteService;

	@PostMapping
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<RetornoDto> create(@RequestBody(required = true) IngredienteDto ingredienteDto) {
		ingredienteService.createIngrediente(ingredienteDto);
		return ResponseEntity.ok(new RetornoDto("OK"));
	}

	@GetMapping(value = "/list")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<List<IngredienteDto>> list(@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer count) {
		return ResponseEntity.ok(ingredienteService.listAll(new PaginacaoDto(page, count)));
	}

	@GetMapping(value = "/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<IngredienteDto> get(@PathVariable("id") Long id) {
		return ResponseEntity.ok(ingredienteService.findDtoById(id));
	}

	@PutMapping
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<RetornoDto> updateProduct(@RequestBody(required = true) IngredienteDto ingredienteDto) {
		ingredienteService.updateIngrediente(ingredienteDto);
		return ResponseEntity.ok(new RetornoDto("OK"));
	}

}
