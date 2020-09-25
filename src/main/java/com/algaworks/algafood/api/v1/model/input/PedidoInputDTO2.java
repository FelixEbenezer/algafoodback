package com.algaworks.algafood.api.v1.model.input;

import java.time.OffsetDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

public class PedidoInputDTO2 {

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
	
	
	//QUEBRA DE COMPTATIBILIDADE
	@NotNull
	@Valid
	private Boolean entregaAgendada;

	@NotNull
	@Valid
	private OffsetDateTime entregaAgendadaPara;
	
	public Boolean getEntregaAgendada() {
		return entregaAgendada;
	}

	public void setEntregaAgendada(Boolean entregaAgendada) {
		this.entregaAgendada = entregaAgendada;
	}

	public OffsetDateTime getEntregaAgendadaPara() {
		return entregaAgendadaPara;
	}

	public void setEntregaAgendadaPara(OffsetDateTime entregaAgendadaPara) {
		this.entregaAgendadaPara = entregaAgendadaPara;
	}

	
	
	
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
