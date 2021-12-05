package br.com.agrostok.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.agrostok.dto.ReturnSaleDto;
import br.com.agrostok.dto.filter.PaginacaoDto;
import br.com.agrostok.service.SaleService;

@RestController
@RequestMapping("/sales")
public class SaleController {

	
	@Autowired
	private SaleService saleService;


	@GetMapping(value = "/list")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<List<ReturnSaleDto>> list(@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer count) {
		return ResponseEntity.ok(saleService.listAll(new PaginacaoDto(page, count)));
	}
	
	@GetMapping(value = "/list2")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<List<ReturnSaleDto>> listByProductName(@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer count) {
		return ResponseEntity.ok(saleService.orderedByHighestSell());
	}
	

}
