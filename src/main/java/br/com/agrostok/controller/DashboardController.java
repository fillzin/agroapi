package br.com.agrostok.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.agrostok.dto.SaledProductDto;
import br.com.agrostok.dto.TotalSaledDto;
import br.com.agrostok.service.DashboardService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	private DashboardService service;


	@GetMapping(value = "/products/top")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<List<SaledProductDto>> list() {
		return ResponseEntity.ok(service.getTopSaleProducts());
	}
	
	@GetMapping(value = "/sales/month")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<List<TotalSaledDto>> listTotalByMonth() {
		return ResponseEntity.ok(service.getSaleTotalPerMonth());
	}
	
	@GetMapping(value = "/products/sales")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<List<SaledProductDto>> listSalesByProduct() {
		return ResponseEntity.ok(service.listSalesByProduct());
	}
	@GetMapping(value = "/revenue")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<SaledProductDto> listReceitaAndDespesa() {
		return ResponseEntity.ok(service.listReceitaAndDespesa());
	}




}
