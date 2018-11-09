package com.dtb.pontointeligente.api.security.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtils {

	static final String CLAIM_KEY_USERNAME = "sub";
	static final String CLAIM_KEY_ROLE = "role";
	static final String CLAIM_KEY_AUDIENCE = "audience";
	static final String CLAIM_KEY_CREATED = "created";

	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.expiration}")
	private Long expiration;

	/**
	 * 
	 * Obtem o usernam (email) do token
	 * 
	 * @param token
	 * @return String
	 * 
	 */

	public String getUsernameFromToken(String token) {
		String username;
		try {
			Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	/**
	 * 
	 * Obtem a data de expiração do token
	 * 
	 * @param token
	 * @return Date
	 * 
	 */

	public Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			Claims claims = getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}

	/**
	 * 
	 * Cria um novo token (Refresh)
	 * 
	 * @param token
	 * @return String
	 * 
	 */

	public String refreshToken(String token) {
		String refreshedToken;
		try {
			Claims claims = getClaimsFromToken(token);
			claims.put(CLAIM_KEY_CREATED, new Date());
			refreshedToken = gerarToken(claims);
		} catch (Exception e) {
			refreshedToken = null;
		}
		return refreshedToken;
	}

	/**
	 * 
	 * Cria um novo token com base nos dados fonercidos pelo UserDetails
	 * 
	 * @param userDetails
	 * @return String
	 * 
	 */

	public String obterToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
		userDetails.getAuthorities().forEach(authority -> claims.put(CLAIM_KEY_ROLE, authority.getAuthority()));
		claims.put(CLAIM_KEY_CREATED, new Date());

		return gerarToken(claims);
	}

	/**
	 * 
	 * Gera um novo token
	 * 
	 * @param claims
	 * @return String
	 * 
	 */

	private String gerarToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setExpiration(gerarDataExpiracao())
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	/**
	 * 
	 * Calcula e retorna uma data de expiração com base na data corrente
	 * 
	 * @return Date
	 * 
	 */

	private Date gerarDataExpiracao() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}

	/**
	 * 
	 * Verifica se o token é valido de acordo com a expiração do mesmo
	 * 
	 * @param token
	 * @return boolean
	 * 
	 */

	public boolean tokenValido(String token) {
		return !tokenExpirado(token);
	}

	/**
	 * 
	 * Verifica se um token está expirado
	 * 
	 * @param token
	 * @return boolean
	 * 
	 */

	private boolean tokenExpirado(String token) {
		Date dataExpiracao = getExpirationDateFromToken(token);
		if (dataExpiracao == null) {
			return false;
		}
		return dataExpiracao.before(new Date());
	}

	/**
	 * 
	 * Extrai as informações contidas no corpo do token
	 * 
	 * @param token
	 * @return Claims
	 * 
	 */

	private Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}
}
