package com.algaworks.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModelProperty;

public class EstadoDTO extends RepresentationModel<EstadoDTO> {

	@ApiModelProperty(example = "1")
	private Long id; 
	
	@ApiModelProperty(example = "Cacuaco")
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
