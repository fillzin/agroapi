package br.com.agrostok.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.agrostok.dto.NotificationDto;
import br.com.agrostok.dto.RetornoDto;
import br.com.agrostok.dto.filter.PaginacaoDto;
import br.com.agrostok.service.NotificationService;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

	@Autowired
	private NotificationService service;


	@GetMapping(value = "/list")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<List<NotificationDto>> list(@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer count) {
		return ResponseEntity.ok(service.listAll(new PaginacaoDto(page, count)));
	}
	
	@GetMapping(value = "/list/unread")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<List<NotificationDto>> listUnread() {
		return ResponseEntity.ok(service.listAllUnread());
	}
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<RetornoDto> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.ok(new RetornoDto("Ok"));
	}
	
	@PutMapping(value = "/{id}/read")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<RetornoDto> read(@PathVariable("id") Long id) {
		service.read(id);
		return ResponseEntity.ok(new RetornoDto("Ok"));
	}


}
