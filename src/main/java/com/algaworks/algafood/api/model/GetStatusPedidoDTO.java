package com.algaworks.algafood.api.model;

import java.time.OffsetDateTime;

public class GetStatusPedidoDTO {

	private OffsetDateTime dataCriacao;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataEntrega;
    private OffsetDateTime dataCancelamento;
    private String status;
    private UsuarioDTO cliente;
    
    
    
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public OffsetDateTime getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(OffsetDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public OffsetDateTime getDataConfirmacao() {
		return dataConfirmacao;
	}
	public void setDataConfirmacao(OffsetDateTime dataConfirmacao) {
		this.dataConfirmacao = dataConfirmacao;
	}
	public OffsetDateTime getDataEntrega() {
		return dataEntrega;
	}
	public void setDataEntrega(OffsetDateTime dataEntrega) {
		this.dataEntrega = dataEntrega;
	}
	public OffsetDateTime getDataCancelamento() {
		return dataCancelamento;
	}
	public void setDataCancelamento(OffsetDateTime dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}
	public UsuarioDTO getCliente() {
		return cliente;
	}
	public void setCliente(UsuarioDTO cliente) {
		this.cliente = cliente;
	}
    
    
}
