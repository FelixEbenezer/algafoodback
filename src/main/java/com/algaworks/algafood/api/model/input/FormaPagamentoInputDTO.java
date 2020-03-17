package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotNull;

public class FormaPagamentoInputDTO {
	
	@NotNull
	private String descricaoNome;

	public String getDescricaoNome() {
		return descricaoNome;
	}

	public void setDescricaoNome(String descricaoNome) {
		this.descricaoNome = descricaoNome;
	} 
	
	

}
