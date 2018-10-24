package com.dtb.pontointeligente.api.services;

import java.util.Optional;

import com.dtb.pontointeligente.api.model.entities.Empresa;

public interface EmpresaService {
	/**
	 *Boa pratica
	 *Retorna uma empresa de acordo com o CNPJ passado 
	 * 
	 * @param cnpj
	 * @return Optional<Empresa>
	 */
	
	Optional<Empresa> buscarPorCnpj(String cnpj);
	/**
	 *Persiste uma empresa no banco de dados 
	 * 
	 * @param empresa
	 * @return empresa
	 */
	
	Empresa adicionar(Empresa empresa);
}
