package com.algaworks.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;

public class FormaPagamentoDTO extends RepresentationModel<FormaPagamentoDTO> {
	
	private Long id;
	
	private String descricaoForma;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricaoForma() {
		return descricaoForma;
	}

	public void setDescricaoForma(String descricaoForma) {
		this.descricaoForma = descricaoForma;
	}
	
	

}
