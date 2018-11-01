package com.dtb.pontointeligente.api.security.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {
	private static final Logger log = LoggerFactory.getLogger(PasswordUtils.class);
	public PasswordUtils() {
		// TODO Auto-generated constructor stub
	}
	public static String gerarBCrypt(String senha) {
		if (senha.isEmpty()) {
			return senha;
		}
		log.info("Gerando hash");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
	}
	public static Boolean checarSenhas(String hash, String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(senha, hash);
	}
}
