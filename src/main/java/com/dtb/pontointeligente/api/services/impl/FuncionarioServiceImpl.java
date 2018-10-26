package com.dtb.pontointeligente.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtb.pontointeligente.api.model.entities.Funcionario;
import com.dtb.pontointeligente.api.model.repositories.FuncionarioRepository;
import com.dtb.pontointeligente.api.services.FuncionarioService;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

	private static final Logger log = LoggerFactory.getLogger(FuncionarioServiceImpl.class);

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Override
	public Optional<Funcionario> buscarPorCpf(String cpf) {
		log.info("Buscando um funcionario para o cpf {}", cpf);
		return Optional.ofNullable(funcionarioRepository.findByCpf(cpf));
	}

	@Override
	public Optional<Funcionario> buscarPorEmail(String email) {
		log.info("Buscando um funcionario para o email {}", email);
		return Optional.ofNullable(funcionarioRepository.findByEmail(email));
	}

	@Override
	public Optional<Funcionario> buscarPorCpfOuEmail(String cpf, String email) {
		log.info("Buscando um funcionario para o cpf {} ou o email {}", cpf, email);
		return Optional.ofNullable(funcionarioRepository.findByCpfOrEmail(cpf, email));
	}


	@Override
	public Optional<Funcionario> buscarPorId(Long id) {
		log.info("Buscando um funcionario para o id {}",id);
		return funcionarioRepository.findById(id);
	}
	
	@Override
	public Funcionario adicionar(Funcionario funcionario) {
		log.info("Persistindo uma entidade Funcionario no banco de dados: {}", funcionario);
		return funcionarioRepository.save(funcionario);
	}

}
