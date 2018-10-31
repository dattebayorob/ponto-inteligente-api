package com.dtb.pontointeligente.api.controller;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dtb.pontointeligente.api.model.dtos.CadastroPJDto;
import com.dtb.pontointeligente.api.model.entities.Empresa;
import com.dtb.pontointeligente.api.model.entities.Funcionario;
import com.dtb.pontointeligente.api.model.enums.PerfilEnum;
import com.dtb.pontointeligente.api.model.response.Response;
import com.dtb.pontointeligente.api.security.utils.PasswordUtils;
import com.dtb.pontointeligente.api.services.EmpresaService;
import com.dtb.pontointeligente.api.services.FuncionarioService;

@RestController
@RequestMapping("/api/cadastrar-pj")
@CrossOrigin(origins ="*")
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
			BindingResult result) throws NoSuchAlgorithmException{
		log.info("Cadastrando PJ {}",cadastroPJDto.toString());
		Response<CadastroPJDto> response = new Response<CadastroPJDto>();
		validarDadosExistentes(cadastroPJDto, result);
		Empresa empresa = converterDtoParaEmpresa(cadastroPJDto);
		Funcionario funcionario = converterDtoParaFuncionario(cadastroPJDto);
		if(result.hasErrors()) {
			log.error("Erro validando dados de cadastro PJ: {}",result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		empresaService.adicionar(empresa);
		funcionario.setEmpresa(empresa);
		funcionarioService.adicionar(funcionario);
		response.setData(converterCadastroPJDto(funcionario));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna um CadastroPJDto com os dados persistidos
	 * 
	 * @param funcionario
	 * @return cadastroPJDto
	 * 
	 * */
	
	private CadastroPJDto converterCadastroPJDto(Funcionario funcionario) {
		CadastroPJDto cadastroPJDTo = new CadastroPJDto();
		cadastroPJDTo.setId(funcionario.getId());
		cadastroPJDTo.setNome(funcionario.getNome());
		cadastroPJDTo.setEmail(funcionario.getEmail());
		cadastroPJDTo.setCpf(funcionario.getCpf());
		cadastroPJDTo.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
		cadastroPJDTo.setCnpj(funcionario.getEmpresa().getCnpj());
		
		return cadastroPJDTo;
	}

	/**
	 * Cria uma entidade Funcionario com os dados do DTO CadastroPJDto
	 * 
	 * @param: cadastroPJDto
	 * @return: Funcionario
	 * 
	 **/
	
	private Funcionario converterDtoParaFuncionario(CadastroPJDto cadastroPJDto) {
		Funcionario funcionario = new Funcionario();
		funcionario.setCpf(cadastroPJDto.getCpf());
		funcionario.setNome(cadastroPJDto.getNome());
		funcionario.setEmail(cadastroPJDto.getEmail());
		funcionario.setPerfil(PerfilEnum.ROLE_ADMIN);
		funcionario.setSenha(PasswordUtils.gerarBCrypt(cadastroPJDto.getSenha()));
		return funcionario;
	}

	/**
	 * Cria uma entidade Empresa com os dados do DTO CadastroPJDto
	 * 
	 * @param: cadastroPJDto
	 * @return: Empresa
	 * 
	 **/
	
	private Empresa converterDtoParaEmpresa(CadastroPJDto cadastroPJDto) {
		Empresa empresa = new Empresa();
		empresa.setCnpj(cadastroPJDto.getCnpj());
		empresa.setRazaoSocial(cadastroPJDto.getRazaoSocial());
		return empresa;
	}
	
	private void validarDadosExistentes(CadastroPJDto cadastroPJDto, BindingResult result) {
		empresaService.buscarPorCnpj(cadastroPJDto.getCnpj())
				.ifPresent(emp -> result.addError(new ObjectError("empresa", "Empresa já existente")));
		funcionarioService.buscarPorCpf(cadastroPJDto.getCpf())
				.ifPresent(func -> result.addError(new ObjectError("funcionario", "CPF já cadastrado")));
		funcionarioService.buscarPorEmail(cadastroPJDto.getEmail())
				.ifPresent(func -> result.addError(new ObjectError("funcionario", "Email já cadastrado")));
		
	}
}
