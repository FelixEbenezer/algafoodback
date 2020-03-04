package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotNull;

public class FormaPagamentoInputDTO {
	
	@NotNull
	private String descricaoForma;

	public String getDescricao() {
		return descricaoForma;
	}

	public void setDescricao(String descricaoForma) {
		this.descricaoForma = descricaoForma;
	} 
	
	

}
