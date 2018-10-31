package com.dtb.pontointeligente.api.model.repositories;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.dtb.pontointeligente.api.model.entities.Empresa;
import com.dtb.pontointeligente.api.model.entities.Funcionario;
import com.dtb.pontointeligente.api.model.enums.PerfilEnum;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioRepositoryTest {
	@Autowired
	FuncionarioRepository funcionarioRepository;
	@Autowired
	EmpresaRepository empresaRepository;
	private static final String CPF = "00000000000";
	private static final String EMAIL = "robson.william65@gmail.com";

	@Before
	public void setUP() {
		Empresa empresa = adicionarEmpresa();
		adicionarFuncionario(empresa);
	}

	private Empresa adicionarEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Empresa de exemplo");
		empresa.setCnpj("00000000000000");
	    empresaRepository.save(empresa);
		return empresa;
	}

	private void adicionarFuncionario(Empresa empresa) {
		Funcionario funcionario = new Funcionario();
		funcionario.setCpf(CPF);
		funcionario.setNome("Robson William da Silva Matos");
		funcionario.setEmail(EMAIL);
		funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
		funcionario.setEmpresa(empresa);
		funcionarioRepository.save(funcionario);
	}

	@After
	public void tearDown() {
		funcionarioRepository.deleteAll();
	}

	@Test
	public void testBuscarFuncionarioPorCpf() {
		Funcionario funcionario = funcionarioRepository.findByCpf(CPF);
		assertEquals(CPF, funcionario.getCpf());
	}

	@Test
	public void testBuscarFuncionarioPorEmail() {

		Funcionario funcionarioPorEmail = funcionarioRepository.findByEmail(EMAIL);

		assertEquals(CPF, funcionarioPorEmail.getCpf());
	}

	@Test
	public void testBuscarFuncionarioPorEmailOrCpf() {

		Funcionario funcionarioPorEmailOrCpf = funcionarioRepository.findByCpfOrEmail("outrocpf", EMAIL);
		assertEquals(CPF, funcionarioPorEmailOrCpf.getCpf());
	}
	@Test
	public void testBuscarEmpresaFuncionario() {
		Funcionario funcionario = funcionarioRepository.findByCpf(CPF);
		assertEquals("00000000000000",funcionario.getEmpresa().getCnpj());
	}
}
