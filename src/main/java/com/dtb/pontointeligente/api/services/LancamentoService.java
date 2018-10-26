package com.dtb.pontointeligente.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.dtb.pontointeligente.api.model.entities.Lancamento;

public interface LancamentoService{
	
	/**
	 * 
	 * buscar os lancamentos paginados para um id 
	 * de funcionario especificado
	 * 
	 * @param funcionarioId
	 * @param pageRequest
	 * @return Page<Lancamento>
	 **/
	
	Page<Lancamento> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest);
	
	/**
	 * 
	 * buscar uma entidade Lancamento do banco de dados de acordo com o id informado
	 * 
	 * @param id
	 * @return Optional<Lancamento>
	 * */
	
	Optional<Lancamento> buscarPorId(Long id);
	
	/**
	 * 
	 * Persistir uma entidade Lancamento no banco de dados
	 * 
	 * @param lancamento
	 * @return Lancamento
	 */

	Lancamento adicionar(Lancamento lancamento);
	
	/**
	 * 
	 * Remover um lancamento do banco de dados
	 * 
	 * @param id
	 * */
	void remover(Long id);
}
