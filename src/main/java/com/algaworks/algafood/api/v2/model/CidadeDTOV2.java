package com.algaworks.algafood.api.v2.model;

import org.springframework.hateoas.RepresentationModel;

public class CidadeDTOV2 extends RepresentationModel<CidadeDTOV2> {
	
	private Long idCidade; 
	
	private String nomeCidade;
	
	private String nomeEstado;
	
	private Long idEstado;

	public Long getIdCidade() {
		return idCidade;
	}

	public void setIdCidade(Long idCidade) {
		this.idCidade = idCidade;
	}

	public String getNomeCidade() {
		return nomeCidade;
	}

	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}

	public String getNomeEstado() {
		return nomeEstado;
	}

	public void setNomeEstado(String nomeEstado) {
		this.nomeEstado = nomeEstado;
	}

	public Long getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Long idEstado) {
		this.idEstado = idEstado;
	} 
	
	

}
