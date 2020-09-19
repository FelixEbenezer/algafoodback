package com.algaworks.algafood.apiexterno.categoriaClient;

import javax.validation.constraints.NotNull;

public class CategoriaClientInput {
	
	@NotNull
	private String nome;
	
	public CategoriaClientInput() {}


	public CategoriaClientInput(String nome2) {
		this.nome=nome2; 
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	

}
