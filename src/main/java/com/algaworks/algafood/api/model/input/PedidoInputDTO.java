package com.algaworks.algafood.api.model.input;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

public class PedidoInputDTO {

	@NotNull
	@Valid
	private RestauranteIdInput restaurante;
	
	@NotNull
	@Valid
	private FormaPagamentoIdInput formaPagamento; 

	@NotNull
	@Valid
	private EnderecoInputDTO enderecoEntrega;
	
	@NotNull
	@Valid
	@Size(min = 1)
	private List<ItemInputDTO> itens;

	public RestauranteIdInput getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(RestauranteIdInput restaurante) {
		this.restaurante = restaurante;
	}

	public FormaPagamentoIdInput getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamentoIdInput formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	

	public EnderecoInputDTO getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(EnderecoInputDTO enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}

	public List<ItemInputDTO> getItens() {
		return itens;
	}

	public void setItens(List<ItemInputDTO> itens) {
		this.itens = itens;
	}
	
	

}
