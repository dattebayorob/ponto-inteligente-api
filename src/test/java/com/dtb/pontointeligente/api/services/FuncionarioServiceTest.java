package com.dtb.pontointeligente.api.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.dtb.pontointeligente.api.model.entities.Funcionario;
import com.dtb.pontointeligente.api.model.repositories.FuncionarioRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioServiceTest {

	/**
	 * Cria uma instancia falsa do funcionarioRepository
	 */
	@MockBean
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private FuncionarioService funcionarioService;
	
	private static final String CPF = "00000000000";

	private static final String EMAIL = "robson.william65@gmail.com";
	
	@Before
	public void setTup() throws Exception{
		BDDMockito.given(funcionarioRepository.findByCpf(Mockito.anyString())).willReturn(new Funcionario());
		BDDMockito.given(funcionarioRepository.findByEmail(Mockito.anyString())).willReturn(new Funcionario());
		BDDMockito.given(funcionarioRepository.findByCpfOrEmail(Mockito.anyString(), Mockito.anyString())).willReturn(new Funcionario());
		BDDMockito.given(funcionarioRepository.findById(Mockito.anyLong())).willReturn(Optional.ofNullable(new Funcionario()));
		BDDMockito.given(funcionarioRepository.save(Mockito.any(Funcionario.class))).willReturn(new Funcionario());
	}
	@Test
	public void testBuscarPorCpf() {
		Optional<Funcionario> funcionario = funcionarioService.buscarPorCpf(CPF);
		assertTrue(funcionario.isPresent());
	}
	@Test
	public void testBuscarPorEmail() {
		Optional <Funcionario> funcionario = funcionarioService.buscarPorEmail(EMAIL);
		assertTrue(funcionario.isPresent());
	}
	@Test
	public void testBuscarPorCpfOuEmail() {
		Optional <Funcionario> funcionario = funcionarioService.buscarPorCpfOuEmail(CPF, EMAIL);
		assertTrue(funcionario.isPresent());
	}
	@Test
	public void testAdicionar() {
		Funcionario funcionario = funcionarioService.adicionar(new Funcionario());
		assertNotNull(funcionario);
	}
	

}
