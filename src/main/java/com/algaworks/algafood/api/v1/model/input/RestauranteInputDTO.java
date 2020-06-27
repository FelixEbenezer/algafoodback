package com.algaworks.algafood.api.v1.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class RestauranteInputDTO {
	
	// private Long id; não metemos o id porque não precisamos dele na hora de criar um novo restaurante 
	
	@NotNull
	private String nome;
	
	@PositiveOrZero
	private BigDecimal taxaFrete;
	
	@Valid
	@NotNull
	private CozinhaIdInput cozinha;
	
	@NotNull
	@Valid
	private EnderecoInputDTO endereco; 
	
	

	public EnderecoInputDTO getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoInputDTO endereco) {
		this.endereco = endereco;
	}

	public void setCozinha(CozinhaIdInput cozinha) {
		this.cozinha = cozinha;
	}
	
	public CozinhaIdInput getCozinha() {
		return cozinha;
	}

	public void setCozinhaId(CozinhaIdInput cozinha) {
		this.cozinha = cozinha;
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

	
}
