package com.dtb.pontointeligente.api.services;

import java.util.Optional;

import com.dtb.pontointeligente.api.model.entities.Funcionario;

public interface FuncionarioService {

	/**
	 * 
	 * Buscar uma entidade Funcionario do banco de dados de acordo com o cpf
	 * informado
	 * 
	 * @param cpf
	 * @return Optional<Funcionario>
	 */

	Optional<Funcionario> buscarPorCpf(String cpf);

	/**
	 * 
	 * Buscar uma entidade Funcionario do banco de dados de acordo com o email
	 * informado
	 * 
	 * @param cpf
	 * @return Optional<Funcionario>
	 * 
	 */

	Optional<Funcionario> buscarPorEmail(String email);

	/**
	 * 
	 * Buscar uma entidade Funcionario do banco de dados de acordo com o cpf ou
	 * email informado
	 * 
	 * @param cpf, email
	 * @return Optional<Funcionario>
	 */
	Optional<Funcionario> buscarPorCpfOuEmail(String cpf, String email);

	/**
	 * 
	 * Buscar uma entidade Funcionario do banco de dados de acordo com o Id
	 * informado
	 * 
	 * @param id
	 * @return Optional<Funcionario>
	 */
	Optional<Funcionario> buscarPorId(Long id);

	/**
	 * 
	 * Persistir uma entidade Funcionario no banco de dados.
	 * 
	 * @param funcionario
	 * @return Funcionario
	 * 
	 */

	Funcionario adicionar(Funcionario funcionario);
}
