package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;

public class EstadoDTO {

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
