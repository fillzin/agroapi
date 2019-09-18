package br.com.agrostok.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.agrostok.dto.LoginDto;
import br.com.agrostok.dto.UserDto;
import br.com.agrostok.entity.User;
import br.com.agrostok.service.UserService;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private UserService userService;

	@PostMapping
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<UserDto> findByLogin(@RequestBody(required = true) LoginDto loginDto) {
		return ResponseEntity.ok(userService.findUserByLoginAndPassword(loginDto.getEmail(), loginDto.getSenha()));
	}
	
	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<User> currentUserName(Authentication authentication) {
		return ResponseEntity.ok((User) authentication.getPrincipal());
	}
}
