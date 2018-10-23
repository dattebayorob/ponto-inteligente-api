package com.dtb.pontointeligente.api.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dtb.pontointeligente.api.model.entities.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{

}
