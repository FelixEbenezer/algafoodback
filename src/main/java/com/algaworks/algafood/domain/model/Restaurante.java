package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
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
import org.springframework.data.domain.AbstractAggregateRoot;

import com.algaworks.algafood.core.validation.Groups;
import com.algaworks.algafood.domain.event.RestauranteFechadoEvent;

@Entity
@Table
// @ValorZeroIncluiDescricao(valorField = "taxaFrete", descricaoField = "nome", descricaoObrigatoria = "Frete Gratis" )
public class Restaurante extends AbstractAggregateRoot<Restaurante> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Boolean ativo = Boolean.TRUE;
	
	private Boolean aberto = Boolean.FALSE; 
	
	public Boolean getAberto() {
		return aberto;
	}

	public void setAberto(Boolean aberto) {
		this.aberto = aberto;
	}

	@NotNull
	private String nome;
	
	@PositiveOrZero
	@Column(name = "tx_frete", nullable = false)
	private BigDecimal taxaFrete;
	
	@NotNull
	@ConvertGroup(from = Default.class, to = Groups.CadastroRestaurante.class)
	@Valid
	// @JsonIgnoreProperties(value = "nome", allowGetters = true)
	// @JsonIgnoreProperties({"hibernateLazyInitializer", "nome"})
	// @ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne
	@JoinColumn(nullable = false)
	private Cozinha cozinha; 
	
	// @JsonIgnore
	@ManyToMany
	@JoinTable(name = "restaurante_forma_pagamento", 
	joinColumns = @JoinColumn(name="restaurante_id"),
	inverseJoinColumns = @JoinColumn(name="forma_pagamento_id"))
	private Set<FormaPagamento> formasPagamento = new HashSet<>();

	// @JsonIgnore
	// @JsonIgnoreProperties("hibernateLazyInitializer")
	
	@Valid
	@Embedded
	private Endereco endereco;
	
	@CreationTimestamp
	@Column(nullable = false)
	private OffsetDateTime dataCadastro; 
	
	@UpdateTimestamp
	@Column(nullable = false)
	private OffsetDateTime dataAtualizacao; 
	
	// @JsonIgnore
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>(); 
	
	
	@ManyToMany
	@JoinTable(name = "restaurante_usuario_responsavel", 
	joinColumns = @JoinColumn(name="restaurante_id"),
	inverseJoinColumns = @JoinColumn(name="usuario_id"))
	private Set<Usuario> responsaveis = new HashSet<>();
	
	
	
	public Set<Usuario> getResponsaveis() {
		return responsaveis;
	}

	public void setResponsaveis(Set<Usuario> responsaveis) {
		this.responsaveis = responsaveis;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public OffsetDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(OffsetDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public OffsetDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(OffsetDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Set<FormaPagamento> getFormasPagamento() {
		return formasPagamento;
	}

	public void setFormasPagamento(Set<FormaPagamento> formasPagamento) {
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
	
	
	public void ativar () {
		setAtivo(true);
	}
	
	public void inativar () {
		setAtivo(false);
	}
	
	public Boolean associarFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().add(formaPagamento);
	}
	
	public Boolean dissociarFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().remove(formaPagamento);
	}
	
	public void abrir() {
		
		setAberto(true);
	}
	
	public void fechar() {
		setAberto(false);
		registerEvent(new RestauranteFechadoEvent(this));
	}
	
	public boolean associarResponsavel (Usuario usuario) {
		return getResponsaveis().add(usuario);
	}
	
	public boolean dissociarResponsavel (Usuario usuario) {
		return getResponsaveis().remove(usuario);
	}
	
	
	
	public boolean aceitaFormaPagamento(FormaPagamento formaPagamento) {
	    return getFormasPagamento().contains(formaPagamento);
	}

	public boolean naoAceitaFormaPagamento(FormaPagamento formaPagamento) {
	    return !aceitaFormaPagamento(formaPagamento);
	}
}
