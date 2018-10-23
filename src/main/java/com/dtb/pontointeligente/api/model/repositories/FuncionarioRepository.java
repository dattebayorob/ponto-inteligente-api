package com.dtb.pontointeligente.api.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dtb.pontointeligente.api.model.entities.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{

}
