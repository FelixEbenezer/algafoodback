package com.algaworks.algafood.domain.model;

import java.util.Arrays;
import java.util.List;

public enum StatusPedido {

	CRIADO ("Criado"),
	CONFIRMADO ("Confirmado", CRIADO),
	ENTREGUE ("Entregue", CONFIRMADO),
	CANCELADO ("Cancelado", CRIADO, CONFIRMADO);
	
	private String descricao; 
	private List<StatusPedido> statusAnteriores; 
	
	private StatusPedido(String descricao, StatusPedido... statusAnteriores ) {
		this.descricao = descricao; 
		this.statusAnteriores = Arrays.asList(statusAnteriores); 
	}

	public boolean naoPodeAlterarPara (StatusPedido novoStatus) {
		 return !novoStatus.statusAnteriores.contains(this);  // o this replaces é o status atual
		}

	
	public List<StatusPedido> getStatusAnteriores() {
		return statusAnteriores;
	}



	public void setStatusAnteriores(List<StatusPedido> statusAnteriores) {
		this.statusAnteriores = statusAnteriores;
	}



	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	
}