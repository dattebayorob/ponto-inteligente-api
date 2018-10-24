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

import com.dtb.pontointeligente.api.model.entities.Empresa;
import com.dtb.pontointeligente.api.model.repositories.EmpresaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpresaServiceTest {
	
	/**
	 *Cria uma instancia falsa do empresaRepository 
	 */
	@MockBean
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private EmpresaService empresaService;
	
	private static final String CNPJ = "00000000000000";
	
	@Before
	public void setTup() throws Exception{
		
		/**
		 * Pra qualquer uso do empresaRepository irá retornar uma nova empresa
		 * */
		BDDMockito.given(empresaRepository.findByCnpj(Mockito.anyString())).willReturn(new Empresa());
		BDDMockito.given(empresaRepository.save(Mockito.any(Empresa.class))).willReturn(new Empresa());
	}
	
	@Test
	public void testBuscarEmpresaPorCnpj() {
		Optional<Empresa> empresa = empresaService.buscarPorCnpj(CNPJ);
		assertTrue(empresa.isPresent());
	}
	
	@Test
	public void testAdicionarEmpresa() {
		Empresa empresa = empresaService.adicionar(new Empresa());
		assertNotNull(empresa);
	}
}
