package com.algaworks.algafood.api.vendaDiariaApi;

import java.math.BigDecimal;
import java.util.Date;

public class VendaDiariaDTO {
	
	
	private Date data;
	private Long totalVendas;
	private BigDecimal totalFaturado;
	// private Long restauranteId;
	
	public VendaDiariaDTO(Date data, /*Long restauranteId,*/ Long totalVendas, BigDecimal totalFaturado) {

		//this.restauranteId = restauranteId;
		this.data = data;
		this.totalVendas = totalVendas;
		this.totalFaturado = totalFaturado;
	}
/*	public Long getRestauranteId() {
		return restauranteId;
	}
	public void setRestauranteId(Long restauranteId) {
		this.restauranteId = restauranteId;
	}*/
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Long getTotalVendas() {
		return totalVendas;
	}
	public void setTotalVendas(Long totalVendas) {
		this.totalVendas = totalVendas;
	}
	public BigDecimal getTotalFaturado() {
		return totalFaturado;
	}
	public void setTotalFaturado(BigDecimal totalFaturado) {
		this.totalFaturado = totalFaturado;
	}
	 
 
}
