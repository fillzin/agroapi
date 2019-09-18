package br.com.agrostok.controller;

import static br.com.gestaoprocesso.enums.TypePersonEnum.CLIENT;

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

import br.com.agrostok.dto.PersonDto;
import br.com.agrostok.dto.RetornoDto;
import br.com.agrostok.dto.filter.PaginacaoDto;
import br.com.agrostok.service.PersonService;

@RestController
@RequestMapping("/clients")
public class ClientController {

	@Autowired
	private PersonService clientService;

	@PostMapping
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<RetornoDto> create(@RequestBody(required = true) PersonDto clientDto) {
		clientDto.setType(CLIENT.getCode());
		clientService.createClient(clientDto);
		return ResponseEntity.ok(new RetornoDto("OK"));
	}

	@GetMapping(value = "/list")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<List<PersonDto>> list(@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer count) {
		return ResponseEntity.ok(clientService.listAll(new PaginacaoDto(page, count), CLIENT));
	}

	@GetMapping(value = "/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<PersonDto> get(@PathVariable("id") Long id) {
		return ResponseEntity.ok(clientService.findDtoById(id));
	}

	@PutMapping
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<RetornoDto> updateProduct(@RequestBody(required = true) PersonDto clientDto) {
		clientDto.setType(CLIENT.getCode());
		clientService.updateClient(clientDto);
		return ResponseEntity.ok(new RetornoDto("OK"));
	}

}
