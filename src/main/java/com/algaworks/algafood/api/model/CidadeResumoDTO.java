package com.algaworks.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;

public class CidadeResumoDTO extends RepresentationModel<CidadeResumoDTO> {
	
	private Long id; 
	
	private String nome; 
	
	private String nomeEstado;

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

	public String getNomeEstado() {
		return nomeEstado;
	}

	public void setNomeEstado(String nomeEstado) {
		this.nomeEstado = nomeEstado;
	} 
	

}
