package com.dtb.pontointeligente.api.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dtb.pontointeligente.api.model.dtos.LancamentoDto;
import com.dtb.pontointeligente.api.model.entities.Funcionario;
import com.dtb.pontointeligente.api.model.entities.Lancamento;
import com.dtb.pontointeligente.api.model.enums.TipoEnum;
import com.dtb.pontointeligente.api.model.response.Response;
import com.dtb.pontointeligente.api.services.FuncionarioService;
import com.dtb.pontointeligente.api.services.LancamentoService;

@RestController
@RequestMapping("/api/lancamentos")
@CrossOrigin(origins = "*")
public class LancamentoController {
	private static final Logger log = LoggerFactory.getLogger(LancamentoController.class);
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private LancamentoService lancamentoService;
	@Autowired
	private FuncionarioService funcionarioService;
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;

	public LancamentoController() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * Retorna todo os lancamentos paginados para o funcionario informado
	 * 
	 * @param idFuncionario
	 * @return ResponseEntity<Response<Page<LancamentoDto>>>
	 * 
	 */

	@GetMapping(value = "/funcionario/{idFuncionario}")
	public ResponseEntity<Response<Page<LancamentoDto>>> listarPorFuncionarioId(
			@PathVariable("idFuncionario") Long idFuncionario, @RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir) {
		log.info("Buscando lancamentos para o funcionario de id: {}, pagina: {}", idFuncionario, pag);
		Response<Page<LancamentoDto>> response = new Response<>();
		PageRequest pageRequest = PageRequest.of(pag, qtdPorPagina, Direction.valueOf(dir), ord);
		Page<Lancamento> lancamentos = lancamentoService.buscarPorFuncionarioId(idFuncionario, pageRequest);
		Page<LancamentoDto> lancamentosDto = lancamentos.map(lancamento -> converterLancamentoDto(lancamento));
		response.setData(lancamentosDto);
		return ResponseEntity.ok(response);
	}

	/**
	 * 
	 * Retorna as informações de um lancamento dado id
	 * 
	 * @param id
	 * @return ResponseEntity<Response<LancamentoDto>>
	 * 
	 */

	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<LancamentoDto>> listarPorId(@PathVariable("id") Long id) {
		log.info("Buscando informações para o lancamento de id {}", id);
		Response<LancamentoDto> response = new Response<>();
		Optional<Lancamento> lancamento = lancamentoService.buscarPorId(id);
		if (!lancamento.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		response.setData(converterLancamentoDto(lancamento.get()));
		return ResponseEntity.ok(response);

	}

	/**
	 * 
	 * Adiciona um novo lancamento
	 * 
	 * @param lancamentoDto
	 * @param result
	 * @throws ParseException
	 * @return ResponseEntity<Response<LancamentoDto>>
	 * 
	 */

	@PostMapping
	public ResponseEntity<Response<LancamentoDto>> adicionar(@Valid @RequestBody LancamentoDto lancamentoDto,
			BindingResult result) throws ParseException {
		log.info("Adicionando lancamento {}", lancamentoDto);
		Response<LancamentoDto> response = new Response<>();
		validarFuncionario(lancamentoDto.getIdFuncionario(), result);
		Lancamento lancamento = converterDtoParaLancamento(lancamentoDto, result);
		if (result.hasErrors()) {
			log.error("Erro validando lancamento {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		lancamentoService.persistir(lancamento);
		lancamentoDto.setId(Optional.of(lancamento.getId()));
		response.setData(lancamentoDto);
		return ResponseEntity.ok(response);
	}

	/**
	 * 
	 * Atualiza um lancamento dado id.
	 * 
	 * @param id
	 * @param lancamentoDto
	 * @param result
	 * @return ResponseEntity<Response<LancamentoDto>>
	 * @throws ParseException
	 * 
	 * 
	 */

	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<LancamentoDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody LancamentoDto lancamentoDto, BindingResult result) throws ParseException {
		log.info("Atualizando lancamento de id {}", id);
		Response<LancamentoDto> response = new Response<>();
		validarFuncionario(lancamentoDto.getIdFuncionario(), result);
		lancamentoDto.setId(Optional.of(id));
		Lancamento lancamento = converterDtoParaLancamento(lancamentoDto, result);
		if (result.hasErrors()) {
			log.info("Erro atualizando lancamento :{}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		lancamento = lancamentoService.persistir(lancamento);
		response.setData(converterLancamentoDto(lancamento));
		return ResponseEntity.ok(response);
	}

	/**
	 * 
	 * Remove um lancamento dado o id
	 * 
	 * @param id
	 * @return ResponseEntity<Response<String>>
	 * 
	 */

	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Removendo lancamento de id {}", id);
		Optional<Lancamento> lancamento = lancamentoService.buscarPorId(id);
		if (!lancamento.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		lancamentoService.remover(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * 
	 * Converte um Dto para uma entidade Lancamento
	 * 
	 * @param lancamentoDto
	 * @param result
	 * @return Lancamento
	 * @throws ParseException
	 * 
	 */

	private Lancamento converterDtoParaLancamento(LancamentoDto lancamentoDto, BindingResult result)
			throws ParseException {
		Lancamento lancamento = new Lancamento();
		if (lancamentoDto.getId().isPresent()) {
			Optional<Lancamento> lancamentoPorId = lancamentoService.buscarPorId(lancamentoDto.getId().get());
			if (lancamentoPorId.isPresent()) {
				lancamento = lancamentoPorId.get();
			} else {
				result.addError(new ObjectError("lancamento", "lancamento não encontrado"));
			}
		}
		lancamento.setData(dateFormat.parse((lancamentoDto.getData())));
		lancamento.setDescricao(lancamentoDto.getDescricao());
		lancamento.setFuncionario(new Funcionario());
		lancamento.getFuncionario().setId(lancamentoDto.getIdFuncionario());
		lancamento.setLocalizacao(lancamentoDto.getLocalizacao());
		if (EnumUtils.isValidEnum(TipoEnum.class, lancamentoDto.getTipo())) {
			lancamento.setTipo(TipoEnum.valueOf(lancamentoDto.getTipo()));
		} else {
			result.addError(new ObjectError("lancamento", "Tipo informado invalido."));
		}
		return lancamento;
	}

	/**
	 * 
	 * Verificar se o usuário informado existe no banco de dados
	 * 
	 * @param id
	 * @param result
	 * 
	 */

	private void validarFuncionario(Long id, BindingResult result) {
		Optional<Funcionario> funcionario = funcionarioService.buscarPorId(id);
		if (!funcionario.isPresent()) {
			result.addError(new ObjectError("funcionario", "Funcionario não encontrado"));
		}
	}

	/**
	 * 
	 * Converte uma entidade Lancamento em um Dto
	 * 
	 * @param lancamento
	 * @return LancamentoDto
	 * 
	 */

	private LancamentoDto converterLancamentoDto(Lancamento lancamento) {
		LancamentoDto lancamentoDto = new LancamentoDto();
		lancamentoDto.setData(String.valueOf(lancamento.getData()));
		lancamentoDto.setDescricao(lancamento.getDescricao());
		lancamentoDto.setId(Optional.ofNullable(lancamento.getId()));
		lancamentoDto.setIdFuncionario(lancamento.getFuncionario().getId());
		lancamentoDto.setLocalizacao(lancamento.getLocalizacao());
		return lancamentoDto;
	}

}
