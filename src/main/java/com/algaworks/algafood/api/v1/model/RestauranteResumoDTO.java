package com.algaworks.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;

public class RestauranteResumoDTO extends RepresentationModel<RestauranteResumoDTO> {
	
    private Long id;
	
	private String nome;

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
	
	

}
