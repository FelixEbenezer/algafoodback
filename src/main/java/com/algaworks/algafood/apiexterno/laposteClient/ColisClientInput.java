package com.algaworks.algafood.apiexterno.laposteClient;

import javax.validation.constraints.NotNull;

public class ColisClientInput {
	
	@NotNull
	private String idColis;
	
	public ColisClientInput() {}


	public ColisClientInput(String nome2) {
		this.idColis=nome2; 
	}

	public String getNome() {
		return idColis;
	}

	public void setNome(String nome) {
		this.idColis = nome;
	}
	
	

}
