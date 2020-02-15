package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.algaworks.algafood.core.validation.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table
public class Restaurante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String nome;
	
	@PositiveOrZero
	@Column(name = "tx_frete", nullable = false)
	private BigDecimal taxaFrete;
	
	@NotNull
	@ConvertGroup(from = Default.class, to = Groups.CadastroRestaurante.class)
	@Valid
	// @JsonIgnore
	@JsonIgnoreProperties("hibernateLazyInitializer")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Cozinha cozinha; 
	
	// @JsonIgnore
	@ManyToMany
	@JoinTable(name = "restaurante_forma_pagamento", 
	joinColumns = @JoinColumn(name="restaurante_id"),
	inverseJoinColumns = @JoinColumn(name="forma_pagamento_id"))
	private List<FormaPagamento> formasPagamento = new ArrayList<>();

	@JsonIgnore
	@JsonIgnoreProperties("hibernateLazyInitializer")
	@Embedded
	private Endereco endereco;
	
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime dataCadastro; 
	
	@UpdateTimestamp
	@Column(nullable = false)
	private LocalDateTime dataAtualizacao; 
	
	@JsonIgnore
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>(); 
	
	
	
	
	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<FormaPagamento> getFormasPagamento() {
		return formasPagamento;
	}

	public void setFormasPagamento(List<FormaPagamento> formasPagamento) {
		this.formasPagamento = formasPagamento;
	}

	public Cozinha getCozinha() {
		return cozinha;
	}

	public void setCozinha(Cozinha cozinha) {
		this.cozinha = cozinha;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getTaxaFrete() {
		return taxaFrete;
	}

	public void setTaxaFrete(BigDecimal taxaFrete) {
		this.taxaFrete = taxaFrete;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Restaurante other = (Restaurante) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
