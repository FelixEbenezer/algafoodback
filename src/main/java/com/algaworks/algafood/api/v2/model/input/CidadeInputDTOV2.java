package com.algaworks.algafood.api.v2.model.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class CidadeInputDTOV2 {
	
	@ApiModelProperty(example = "Cacuaco")
	@NotNull
	private String nomeCidade; 
	
	@ApiModelProperty(example = "1")
	@NotNull
	private Long idEstado;

	public String getNomeCidade() {
		return nomeCidade;
	}

	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}

	public Long getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Long idEstado) {
		this.idEstado = idEstado;
	} 



}
