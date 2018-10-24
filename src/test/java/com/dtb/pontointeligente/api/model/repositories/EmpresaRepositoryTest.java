package com.dtb.pontointeligente.api.model.repositories;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.dtb.pontointeligente.api.model.entities.Empresa;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpresaRepositoryTest {
	@Autowired
	private EmpresaRepository empresaRepository;
	
	private static final String CNPJ="00000000000000";
	
	@Before
	public void setUp() throws Exception{
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Empresa de exemplo");
		empresa.setCnpj(CNPJ);
	    empresaRepository.save(empresa);
	}
	@After
	public final void tearDown() {
		empresaRepository.deleteAll();
	}
	@Test
	public void testBuscarPorCnpj() {
		Empresa empresa = empresaRepository.findByCnpj(CNPJ);
		assertEquals(CNPJ, empresa.getCnpj());
	}
}
