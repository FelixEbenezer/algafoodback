package com.algaworks.algafood.praticas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Demande {
		
	
	private BigDecimal taxaFrete;
	
	private BigDecimal subTotal; 
	
	private BigDecimal valorTotal;
	
	
	
	public Demande(BigDecimal taxaFrete, BigDecimal subTotal, BigDecimal valorTotal) {

		this.taxaFrete = taxaFrete;
		this.subTotal = subTotal;
		this.valorTotal = valorTotal;
	}

	List<Item> itens = new ArrayList<>();
	
	public BigDecimal calcularSubTotal() {
	return 	itens.stream()
			.map(i -> i.getPrecoTotal())
			.reduce(BigDecimal.ZERO, (a,b) -> a.add(b));
		  // .reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	public void calcularValorTotal() {
		this.valorTotal = calcularSubTotal().add(this.taxaFrete);
	}

	public BigDecimal getTaxaFrete() {
		return taxaFrete;
	}

	public void setTaxaFrete(BigDecimal taxaFrete) {
		this.taxaFrete = taxaFrete;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	} 
	
	
	

}
