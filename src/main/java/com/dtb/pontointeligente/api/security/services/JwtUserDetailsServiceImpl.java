package com.dtb.pontointeligente.api.security.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dtb.pontointeligente.api.model.entities.Funcionario;
import com.dtb.pontointeligente.api.security.JwtUserFactory;
import com.dtb.pontointeligente.api.services.FuncionarioService;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private FuncionarioService funcionarioService;
	
		
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Funcionario> funcionario = funcionarioService.buscarPorEmail(username);
		if(!funcionario.isPresent()) {
			throw new UsernameNotFoundException("Email não encontrado");
		}
		return JwtUserFactory.create(funcionario.get());
	}

}
