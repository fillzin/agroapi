package br.com.agrostok.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.agrostok.dto.RetornoDto;
import br.com.agrostok.dto.SaleDto;
import br.com.agrostok.dto.StockDto;
import br.com.agrostok.dto.StockHistDto;
import br.com.agrostok.dto.filter.PaginacaoDto;
import br.com.agrostok.service.SaleService;
import br.com.agrostok.service.StockHistService;
import br.com.agrostok.service.StockService;

@RestController
@RequestMapping("/stocks")
public class StockController {

	@Autowired
	private StockService stockService;
	
	@Autowired
	private StockHistService stockHistService;
	
	@Autowired
	private SaleService saleService;



	
	@PostMapping(value = "/sale")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<RetornoDto> sale(@RequestBody(required = true) SaleDto saleDto) {
		saleService.sale(saleDto);
		return ResponseEntity.ok(new RetornoDto("OK"));
	}
	
	@PostMapping(value = "/input")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<RetornoDto> input(@RequestBody(required = true) SaleDto saleDto) {
		stockService.input(saleDto);
		return ResponseEntity.ok(new RetornoDto("OK"));
	}
	
	@GetMapping(value = "/entradas")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<List<StockDto>> listHistory(@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer count, @RequestParam(required = false) String name) {
		return ResponseEntity.ok(stockService.listAllEntradas(new PaginacaoDto(page, count), name));
	}
	@GetMapping(value = "/client")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<List<SaleDto>> client(@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer count) {
		return ResponseEntity.ok(saleService.orderByClient(new PaginacaoDto(page, count)));
	}

//	@GetMapping(value = "/{id}")
//	@ResponseStatus(value = HttpStatus.OK)
//	public ResponseEntity<PersonDto> get(@PathVariable("id") Long id) {
//		return ResponseEntity.ok(clientService.findDtoById(id));
//	}
//
//	@PutMapping
//	@ResponseStatus(value = HttpStatus.OK)
//	public ResponseEntity<RetornoDto> updateProduct(@RequestBody(required = true) PersonDto clientDto) {
//		clientDto.setType(CLIENT.getCode());
//		clientService.updateClient(clientDto);
//		return ResponseEntity.ok(new RetornoDto("OK"));
//	}

}
