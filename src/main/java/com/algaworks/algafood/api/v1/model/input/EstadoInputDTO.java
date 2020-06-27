package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.NotNull;

public class EstadoInputDTO {
	
	@NotNull
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	} 
	
	

}
