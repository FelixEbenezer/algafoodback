package com.algaworks.algafood.api.model;

import java.math.BigDecimal;
import java.util.List;

public class RestauranteDTO {
	
	private Long id;
	
	private String nome; 
	
	private BigDecimal precoFrete;
	
	private CozinhaDTO cozinha;
	
	private Boolean ativo;
	
	private Boolean aberto; 
	
	public Boolean getAberto() {
		return aberto;
	}

	public void setAberto(Boolean aberto) {
		this.aberto = aberto;
	}

	private EnderecoDTO endereco; 
	
	private List<FormaPagamentoDTO> formasPagamento; 
	
/*	private List<FormaPagamento> formasPagamento;

	public List<FormaPagamento> getFormasPagamento() {
		return formasPagamento;
	}

	public void setFormasPagamento(List<FormaPagamento> formasPagamento) {
		this.formasPagamento = formasPagamento;
	}
*/
	
	
	public List<FormaPagamentoDTO> getFormasPagamento() {
		return formasPagamento;
	}

	public void setFormasPagamento(List<FormaPagamentoDTO> formasPagamento) {
		this.formasPagamento = formasPagamento;
	}

	public Long getId() {
		return id;
	}

	public EnderecoDTO getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoDTO endereco) {
		this.endereco = endereco;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
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

	public BigDecimal getPrecoFrete() {
		return precoFrete;
	}

	public void setPrecoFrete(BigDecimal precoFrete) {
		this.precoFrete = precoFrete;
	}

	public CozinhaDTO getCozinha() {
		return cozinha;
	}

	public void setCozinha(CozinhaDTO cozinha) {
		this.cozinha = cozinha;
	} 
	
	
	

}
