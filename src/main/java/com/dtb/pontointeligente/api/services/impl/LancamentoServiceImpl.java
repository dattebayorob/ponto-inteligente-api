package com.dtb.pontointeligente.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.dtb.pontointeligente.api.model.entities.Lancamento;
import com.dtb.pontointeligente.api.model.repositories.LancamentoRepository;
import com.dtb.pontointeligente.api.services.LancamentoService;

@Service
public class LancamentoServiceImpl implements LancamentoService{
	
	private static final Logger log = LoggerFactory.getLogger(LancamentoServiceImpl.class);
	@Autowired
	private LancamentoRepository lancamentoRepository;
	@Override
	public Page<Lancamento> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest) {
		log.info("Buscando Lancamento pelo ID {} do funcionario",funcionarioId);
		return lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest);
	}
	@Cacheable("lancamentoPorId")
	@Override
	public Optional<Lancamento> buscarPorId(Long id) {
		log.info("Buscando o Lancamento de ID {}",id);
		return lancamentoRepository.findById(id);
	}
	@CachePut("lancamentoPorId")
	@Override
	public Lancamento persistir(Lancamento lancamento) {
		log.info("Persistindo um Lancamento no banco de dados: {}",lancamento);
		return lancamentoRepository.save(lancamento);
	}
	@Override
	public void remover(Long id) {
		log.info("Removendo lancamento de ID {}",id);
		lancamentoRepository.deleteById(id);
	}
}
