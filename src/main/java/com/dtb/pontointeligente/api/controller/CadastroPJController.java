package com.dtb.pontointeligente.api.controller;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dtb.pontointeligente.api.model.dtos.CadastroPJDto;
import com.dtb.pontointeligente.api.model.response.Response;
import com.dtb.pontointeligente.api.services.EmpresaService;
import com.dtb.pontointeligente.api.services.FuncionarioService;

@RestController
@RequestMapping("/api/cadastrar-pj")
@CrossOrigin("*")
public class CadastroPJController {
	
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(CadastroPJController.class); 
	@Autowired
	private FuncionarioService funcionarioService;
	@Autowired
	private EmpresaService empresaService;
	public CadastroPJController() {
		// TODO Auto-generated constructor stub
	}
	
	@PostMapping
	public ResponseEntity<Response<CadastroPJDto>> cadastrar(@Valid @RequestBody CadastroPJDto cadastroPJDto,
			BindingResult bindingResult) throws NoSuchAlgorithmException{
		log.info("## - Cadastrando PJ {} - ##",cadastroPJDto.toString());
		return null;
	}
}
