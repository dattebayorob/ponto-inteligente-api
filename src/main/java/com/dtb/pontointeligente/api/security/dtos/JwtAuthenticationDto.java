package com.dtb.pontointeligente.api.security.dtos;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class JwtAuthenticationDto {
	
	@NotEmpty(message="Email (Usuario) não pode ser vazio")
	@Email(message="Email invalido.")
	private String email;
	@NotEmpty(message="A senha não pode ser vazia")
	private String senha;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	@Override
	public String toString() {
		return "JwtAuthenticationDto [email=" + email + ", senha=" + senha + "]";
	}
	
	
}
