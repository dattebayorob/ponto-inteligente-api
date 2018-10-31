package com.dtb.pontointeligente.api.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dtb.pontointeligente.api.model.dtos.EmpresaDto;
import com.dtb.pontointeligente.api.model.entities.Empresa;
import com.dtb.pontointeligente.api.model.response.Response;
import com.dtb.pontointeligente.api.services.EmpresaService;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {
	private static final Logger log  = LoggerFactory.getLogger(EmpresaController.class);
	@Autowired
	private EmpresaService empresaService;
	
	@GetMapping(value="/cnpj/{cnpj}")
	public ResponseEntity<Response<EmpresaDto>> buscarPorCnpj(@PathVariable("cnpj") String cnpj){
		log.info("Buscando empresa pelo CNPJ {}",cnpj);
		Response<EmpresaDto> response = new Response<>();
		Optional<Empresa> empresa = empresaService.buscarPorCnpj(cnpj);
		if(!empresa.isPresent()) {
			log.info("Empresa nao encontrado para o CNPJ {}",cnpj);
			response.getErros().add("Empresa não encontrada para o CNPJ "+cnpj);
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(converterEmpresaDto(empresa.get()));
		return ResponseEntity.ok(response);
	}

	private EmpresaDto converterEmpresaDto(Empresa empresa) {
		EmpresaDto empresaDto = new EmpresaDto();
		empresaDto.setCnpj(empresa.getCnpj());
		empresaDto.setId(empresa.getId());
		empresaDto.setRazaoSocial(empresa.getRazaoSocial());
		return empresaDto;
	}
}
