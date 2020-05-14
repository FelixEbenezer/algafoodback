package com.algaworks.algafood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class CidadeInputDTO {
	
	@ApiModelProperty(example = "Cacuaco")
	@NotNull
	private String nome; 
	
	@ApiModelProperty(example = "1")
	@NotNull
	@Valid
	private EstadoIdInput estado;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public EstadoIdInput getEstado() {
		return estado;
	}

	public void setEstado(EstadoIdInput estado) {
		this.estado = estado;
	} 
	
	

}
