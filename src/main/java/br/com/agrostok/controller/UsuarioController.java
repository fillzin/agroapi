package br.com.agrostok.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.agrostok.dto.RetornoDto;
import br.com.agrostok.dto.UserDto;
import br.com.agrostok.service.UserService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<String> listAll() {
		return ResponseEntity.ok("ok");
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<RetornoDto> criar(@RequestBody(required = true) UserDto userDto) {
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		userService.createUser(userDto);
		return ResponseEntity.ok(new RetornoDto("OK"));
	}
	
	@PutMapping
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<RetornoDto> update(@RequestBody(required = true) UserDto userDto) {
		userService.updateUser(userDto);
		return ResponseEntity.ok(new RetornoDto("OK"));
	}
	

	
}
