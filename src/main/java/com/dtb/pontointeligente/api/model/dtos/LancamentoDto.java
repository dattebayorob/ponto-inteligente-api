package com.dtb.pontointeligente.api.model.dtos;

import java.util.Optional;

import org.hibernate.validator.constraints.NotEmpty;

public class LancamentoDto {
	private Optional<Long> id = Optional.empty();
	@NotEmpty(message="Data nao pode ser vazia.")
	private String data;
	@NotEmpty(message="Tipo não pode ser vazio.")
	private String tipo;
	private String descricao;
	private String localizacao;
	private Long idFuncionario;

	public LancamentoDto() {
		// TODO Auto-generated constructor stub
	}

	public Optional<Long> getId() {
		return id;
	}

	public void setId(Optional<Long> id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public Long getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Long idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	@Override
	public String toString() {
		return "LancamentoDto [id=" + id + ", data=" + data + ", tipo=" + tipo + ", descricao=" + descricao
				+ ", localizacao=" + localizacao + ", idFuncionario=" + idFuncionario + "]";
	}

}
