package br.com.agrostok.dto;

public class UserDto {

	private Long id;
	private Boolean enabled;
	private String email;
	private String username;
	private String nome;
	private String password;

	public UserDto() {
	}

	public UserDto(UserDto user) {
		this.id = user.id;
		this.enabled = user.enabled;
		this.email = user.email;
		this.username = user.username;
		this.nome = user.nome;
		this.password = user.password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
