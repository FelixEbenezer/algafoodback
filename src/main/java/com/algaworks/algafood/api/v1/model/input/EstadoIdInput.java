package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class EstadoIdInput {
	
	@ApiModelProperty(example = "1")
	@NotNull
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	} 
	
	

}
