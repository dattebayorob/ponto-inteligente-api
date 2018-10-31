package com.dtb.pontointeligente.api.controller;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

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

import com.dtb.pontointeligente.api.model.dtos.CadastroPFDto;
import com.dtb.pontointeligente.api.model.entities.Empresa;
import com.dtb.pontointeligente.api.model.entities.Funcionario;
import com.dtb.pontointeligente.api.model.enums.PerfilEnum;
import com.dtb.pontointeligente.api.model.response.Response;
import com.dtb.pontointeligente.api.security.utils.PasswordUtils;
import com.dtb.pontointeligente.api.services.EmpresaService;
import com.dtb.pontointeligente.api.services.FuncionarioService;

@RestController
@RequestMapping("/api/cadastrar-pf")
@CrossOrigin(origins="*")
public class CadastroPFController {
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(CadastroPFController.class);

	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private FuncionarioService funcionarioService;

	public CadastroPFController() {
		// TODO Auto-generated constructor stub
	}

	@PostMapping
	public ResponseEntity<Response<CadastroPFDto>> cadastrar(@Valid @RequestBody CadastroPFDto cadastroPFDto,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Cadastrando PF: {}", cadastroPFDto.toString());
		Response<CadastroPFDto> response = new Response<>();
		validarDadosExistentes(cadastroPFDto, result);
		Funcionario funcionario = converterDtoParaFuncionario(cadastroPFDto, result);
		if (result.hasErrors()) {
			log.error("Erro validando cadastro PF: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		Optional<Empresa> empresa = empresaService.buscarPorCnpj(cadastroPFDto.getCnpj());
		empresa.ifPresent(emp -> funcionario.setEmpresa(emp));
		funcionarioService.adicionar(funcionario);
		response.setData(converterCadastroPFDto(funcionario));
		return ResponseEntity.ok(response);
	}

	private CadastroPFDto converterCadastroPFDto(Funcionario funcionario) {
		CadastroPFDto cadastroPFDTo = new CadastroPFDto();
		cadastroPFDTo.setId(funcionario.getId());
		cadastroPFDTo.setNome(funcionario.getNome());
		cadastroPFDTo.setEmail(funcionario.getEmail());
		cadastroPFDTo.setCpf(funcionario.getCpf());
		cadastroPFDTo.setCnpj(funcionario.getEmpresa().getCnpj());
		cadastroPFDTo.setQtdHorasAlmoco(Optional
				.ofNullable(String.valueOf(funcionario.getQtdHorasAlmoco())));
		cadastroPFDTo.setQtdHorasTrabalhoDia(Optional
				.ofNullable(String.valueOf(funcionario.getQtdHorasTrabalhoDia())));
		cadastroPFDTo.setValorHora(Optional
				.ofNullable(String.valueOf(funcionario.getValorHora())));
		return cadastroPFDTo;
	}

	private Funcionario converterDtoParaFuncionario(CadastroPFDto cadastroPFDto, BindingResult result) {
		Funcionario funcionario = new Funcionario();
		funcionario.setCpf(cadastroPFDto.getCpf());
		funcionario.setNome(cadastroPFDto.getNome());
		funcionario.setEmail(cadastroPFDto.getEmail());
		funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
		funcionario.setSenha(PasswordUtils.gerarBCrypt(cadastroPFDto.getSenha()));
		cadastroPFDto.getQtdHorasAlmoco()
				.ifPresent(qtdHorasAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));
		cadastroPFDto.getQtdHorasAlmoco()
				.ifPresent(qtdHorasAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));
		cadastroPFDto.getValorHora().ifPresent(valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));

		return funcionario;
	}

	private void validarDadosExistentes(CadastroPFDto cadastroPFDto, BindingResult result) {
		Optional<Empresa> empresa = empresaService.buscarPorCnpj(cadastroPFDto.getCnpj());
		if (!empresa.isPresent()) {
			result.addError(new ObjectError("empresa", "Empresa não cadastrada"));
		}
		funcionarioService.buscarPorCpf(cadastroPFDto.getCpf())
				.ifPresent(func -> result.addError(new ObjectError("funcionario", "CPF já cadastrado")));
		funcionarioService.buscarPorEmail(cadastroPFDto.getEmail())
				.ifPresent(func -> result.addError(new ObjectError("funcionario", "Email já cadastrado")));

	}

}
