package com.algaworks.algafood.api.v1.model.input;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

public class PermissaoInputDTO {

	@NotNull
	@Column(nullable = false)
	private String nome; 
	
	private String descricao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
