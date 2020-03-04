package com.algaworks.algafood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class CidadeInputDTO {
	
	@NotNull
	private String nome; 
	
	@NotNull
	@Valid
	private EstadoIdInput nomeEstado;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public EstadoIdInput getNomeEstado() {
		return nomeEstado;
	}

	public void setNomeEstado(EstadoIdInput nomeEstado) {
		this.nomeEstado = nomeEstado;
	} 
	
	

}