package com.dtb.pontointeligente.api.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class BasicSecurity extends WebSecurityConfigurerAdapter {
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
      throws Exception {
        auth
          .inMemoryAuthentication()
          .withUser("admin")
            .password("$2a$10$KnMisxgCqONRa3iO57d62.s7BptTTbrrlMKZJo.xcpb.dQrjFlqNK")
            .roles("USER","ADMIN")
            .and()
            	.withUser("user")
            	.password("$2a$10$XKWtpBkTxzc6NnbCN3n0OeH1MTTPqWmpDyd4jIt1ARGUqz.Z.rJEK")
            	.roles("USER");
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
          .authorizeRequests()
          .anyRequest()
          .authenticated()
          .and()
          .httpBasic()
          .and()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.csrf().disable();;
    }
    @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
}
