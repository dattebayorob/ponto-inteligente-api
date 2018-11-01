package com.dtb.pontointeligente.api.controller;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dtb.pontointeligente.api.model.dtos.FuncionarioDto;
import com.dtb.pontointeligente.api.model.entities.Funcionario;
import com.dtb.pontointeligente.api.model.response.Response;
import com.dtb.pontointeligente.api.security.utils.PasswordUtils;
import com.dtb.pontointeligente.api.services.FuncionarioService;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "*")
public class FuncionarioController {
	private static final Logger log = LoggerFactory.getLogger(FuncionarioController.class);
	@Autowired
	private FuncionarioService funcionarioService;

	public FuncionarioController() {
		// TODO Auto-generated constructor stub
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<FuncionarioDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody FuncionarioDto funcionarioDto, BindingResult result) throws NoSuchAlgorithmException {
		Response<FuncionarioDto> response = new Response<>();
		Optional<Funcionario> funcionario = funcionarioService.buscarPorId(id);
		if (!funcionario.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		validarEAtualizarDadosFuncionario(funcionario.get(), funcionarioDto, result);
		if (result.hasErrors()) {
			log.error("Erro validando funcionario: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		funcionarioService.adicionar(funcionario.get());
		response.setData(converterFuncionarioDto(funcionario.get()));
		return ResponseEntity.ok(response);
	}

	private FuncionarioDto converterFuncionarioDto(Funcionario funcionario) {
		FuncionarioDto funcionarioDto = new FuncionarioDto();
		funcionarioDto.setEmail(funcionario.getEmail());
		funcionarioDto.setId(funcionario.getId());
		funcionarioDto.setNome(funcionario.getNome());
		funcionarioDto.setQtdHorasAlmoco(Optional.ofNullable(String.valueOf(funcionario.getQtdHorasAlmoco())));
		funcionarioDto
				.setQtdHorasTrabalhoDia(Optional.ofNullable(String.valueOf(funcionario.getQtdHorasTrabalhoDia())));
		funcionarioDto.setValorHora(Optional.ofNullable(String.valueOf(funcionario.getValorHora())));
		return funcionarioDto;
	}

	private void validarEAtualizarDadosFuncionario(Funcionario funcionario, FuncionarioDto funcionarioDto,
			BindingResult result) {
		funcionario.setNome(funcionarioDto.getNome());
		if (!funcionario.getEmail().equals(funcionarioDto.getEmail())) {
			funcionarioService.buscarPorEmail(funcionarioDto.getEmail()).ifPresent(funcionarioPorEmail -> result
					.addError(new ObjectError("email", "Email informado na atualização dos dados já existe.")));
			funcionario.setEmail(funcionarioDto.getEmail());
		}
		funcionario.setQtdHorasAlmoco(null);
		funcionarioDto.getQtdHorasAlmoco()
				.ifPresent(qtdHorasAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));

		funcionario.setQtdHorasTrabalhoDia(null);
		funcionarioDto.getQtdHorasTrabalhoDia().ifPresent(
				qtdHorasTrabalhoDia -> funcionario.setQtdHorasTrabalhoDia(Float.valueOf(qtdHorasTrabalhoDia)));

		funcionario.setValorHora(null);
		funcionarioDto.getValorHora().ifPresent(valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));
		if (funcionarioDto.getSenha().isPresent()) {
			funcionario.setSenha(PasswordUtils.gerarBCrypt(funcionarioDto.getSenha().get()));
		}

	}
}
