package com.algaworks.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class PedidoDTO {
	
	private Long id;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private String status;
    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataEntrega;
    private OffsetDateTime dataCancelamento;
    private RestauranteResumoDTO restaurante;
    private UsuarioDTO cliente;
    private FormaPagamentoDTO formaPagamento;
    private EnderecoDTO enderecoEntrega;
    private List<ItemPedidoDTO> itens;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}
	public BigDecimal getTaxaFrete() {
		return taxaFrete;
	}
	public void setTaxaFrete(BigDecimal taxaFrete) {
		this.taxaFrete = taxaFrete;
	}
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
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
	public RestauranteResumoDTO getRestaurante() {
		return restaurante;
	}
	public void setRestaurante(RestauranteResumoDTO restaurante) {
		this.restaurante = restaurante;
	}
	public UsuarioDTO getCliente() {
		return cliente;
	}
	public void setCliente(UsuarioDTO cliente) {
		this.cliente = cliente;
	}
	public FormaPagamentoDTO getFormaPagamento() {
		return formaPagamento;
	}
	public void setFormaPagamento(FormaPagamentoDTO formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	public EnderecoDTO getEnderecoEntrega() {
		return enderecoEntrega;
	}
	public void setEnderecoEntrega(EnderecoDTO enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}
	public List<ItemPedidoDTO> getItens() {
		return itens;
	}
	public void setItens(List<ItemPedidoDTO> itens) {
		this.itens = itens;
	}
    
    
    
    
}
