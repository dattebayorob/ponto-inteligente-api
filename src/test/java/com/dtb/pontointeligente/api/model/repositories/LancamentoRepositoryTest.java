package com.dtb.pontointeligente.api.model.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.dtb.pontointeligente.api.model.entities.Empresa;
import com.dtb.pontointeligente.api.model.entities.Funcionario;
import com.dtb.pontointeligente.api.model.entities.Lancamento;
import com.dtb.pontointeligente.api.model.enums.PerfilEnum;
import com.dtb.pontointeligente.api.model.enums.TipoEnum;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoRepositoryTest {
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	@Autowired
	private EmpresaRepository empresaRepository;
	private static final String CNPJ="00000000000000";
	private static final String CPF = "00000000000";
	private static final String EMAIL = "robson.william65@gmail.com";
	private Long funcionarioId;
	@Before
	public void setTup() {
		Empresa empresa = adicionarEmpresa();
		Funcionario funcionario = adicionarFuncionario(empresa);
		adicionarLancamento(funcionario);
		adicionarLancamento(funcionario);
	}
	@After
	public void tearDown() {
		lancamentoRepository.deleteAll();
		funcionarioRepository.deleteAll();
		empresaRepository.deleteAll();
	}
	
	@Test
	public void buscarLancamentoPorFuncionario() {
		List<Lancamento> lancamentos = lancamentoRepository.findByFuncionarioId(funcionarioId);
		assertNotNull(lancamentos);
	}
	@Test
	public void buscarQuantidadeLancamentosPorFuncionario() {
		List<Lancamento> lancamentos = lancamentoRepository.findByFuncionarioId(funcionarioId);
		assertEquals(2, lancamentos.size());
	}
	@Test
	public void buscarQuantidadeLancamentosPorFuncionarioPaginado() {
		PageRequest page = PageRequest.of(0, 10);
		Page<Lancamento> lancamentos = lancamentoRepository.findByFuncionarioId(funcionarioId, page);
		assertEquals(2, lancamentos.getTotalElements());
	}
	
	
	
	
	private Empresa adicionarEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Empresa de exemplo");
		empresa.setCnpj("00000000000000");
	    empresaRepository.save(empresa);
		return empresa;
	}

	private Funcionario adicionarFuncionario(Empresa empresa) {
		Funcionario funcionario = new Funcionario();
		funcionario.setCpf(CPF);
		funcionario.setNome("Robson William da Silva Matos");
		funcionario.setEmail(EMAIL);
		funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
		funcionario.setValorHora(new BigDecimal("12.0"));
		funcionario.setEmpresa(empresa);
		funcionarioRepository.save(funcionario);
		this.funcionarioId = funcionario.getId();
		return funcionario;
	}
	private void adicionarLancamento(Funcionario funcionario) {
		Lancamento lancamento = new Lancamento();
		lancamento.setData(new Date());
		lancamento.setFuncionario(funcionario);
		lancamento.setLocalizacao("TESTE");
		lancamento.setTipo(TipoEnum.INICIO_TRABALHO);
		lancamentoRepository.save(lancamento);
	}
}
