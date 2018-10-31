package com.dtb.pontointeligente.api.security.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PasswordUtilsTest {
	private static final String senha="admin";
	@Test
	public void testEncode() {
		BCryptPasswordEncoder bcrypte = new BCryptPasswordEncoder();
		String senhaEncodada = PasswordUtils.gerarBCrypt(senha);
		System.out.println(senhaEncodada);
		assertTrue(bcrypte.matches(senha, senhaEncodada));
	}
}
