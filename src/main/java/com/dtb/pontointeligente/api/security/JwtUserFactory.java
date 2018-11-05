package com.dtb.pontointeligente.api.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.dtb.pontointeligente.api.model.entities.Funcionario;
import com.dtb.pontointeligente.api.model.enums.PerfilEnum;

public class JwtUserFactory {

	/**
	 * 
	 * Prove as informações do usuário ao JwtUser com as informações obtidas pela
	 * entidade Funcionario
	 * 
	 * @param funcionario
	 * @return JwtUser
	 * 
	 */
	
	public static JwtUser create(Funcionario funcionario) {
		return new JwtUser(funcionario.getId(), funcionario.getEmail(), funcionario.getSenha(),
				mapToGrantedAuthorities(funcionario.getPerfil()));
	}

	/**
	 * 
	 * Converte um PerfilEnum para uma GrantedAuthority list
	 * @param perfil
	 * @return List<? extends GrantedAuthority>
	 * 
	 * */
	
	private static List<? extends GrantedAuthority> mapToGrantedAuthorities(PerfilEnum perfil) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(perfil.toString()));
		return authorities;
	}
}
