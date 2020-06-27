package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.PositiveOrZero;

import com.sun.istack.NotNull;

public class ItemInputDTO {
	
	@NotNull
	private Long produtoId;
    
	@NotNull
	@PositiveOrZero
	private Integer quantidade;
    
	private String observacao;
    
	public Long getProdutoId() {
		return produtoId;
	}
	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
    
    


}
