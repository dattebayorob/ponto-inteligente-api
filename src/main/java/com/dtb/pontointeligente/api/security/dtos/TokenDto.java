package com.dtb.pontointeligente.api.security.dtos;

public class TokenDto {
	private String token;
	
	private void TOkenDto() {
		// TODO Auto-generated method stub

	}
	public TokenDto(String token) {
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
