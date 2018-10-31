package com.dtb.pontointeligente.api.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import com.dtb.pontointeligente.api.config.PontoInteligenteProperties;

@Entity
@Table(name = "empresa")
public class Empresa implements Serializable {
	private static final long serialVersionUID = 4674523573879048312L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable=false)
	private String cnpj;
	@Column(name = "razao_social", nullable = false)
	private String razaoSocial;
	@Column(name = "data_criacao", nullable = false)
	private Date dataCriacao;
	@Column(name = "data_atualizacao", nullable=false)
	private Date dataAtualizacao;
	@OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Funcionario> funcionarios;

	public Empresa() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}
	
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@PreUpdate
	public void preUpdate() {
		dataAtualizacao = new Date();
	}

	@PrePersist
	public void prePersist() {
		final Date atual = new Date();
		dataCriacao = atual;
		dataAtualizacao = atual;
	}

	@Override
	public String toString() {
		return "Empresa [id=" + id + ",cnpj="+cnpj+", razaoSocial=" + razaoSocial + ", dataCriacao=" + dataCriacao
				+ ", dataAtualizacao=" + dataAtualizacao + ", funcionarios=" + funcionarios + "]";
	}
	
}
