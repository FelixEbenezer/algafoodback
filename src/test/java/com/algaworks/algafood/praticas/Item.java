package com.algaworks.algafood.praticas;

import java.math.BigDecimal;

public class Item {
	
	private String produtoNome; 
	
	private int qde; 
	
	private BigDecimal precoUnitario; 
	
	private BigDecimal precoTotal;

	public Item(String produtoNome, int qde, BigDecimal precoUnitario, BigDecimal precoTotal) {
		this.produtoNome = produtoNome;
		this.qde = qde;
		this.precoUnitario = precoUnitario;
		this.precoTotal = precoTotal;
	}

	public void calcularPrecoTotal() {
		//this.precoTotal = this.qde * this.precoUnitario;
		this.precoTotal = this.precoUnitario.multiply(new BigDecimal(this.qde));
	}
	
	public String getProdutoNome() {
		return produtoNome;
	}

	public void setProdutoNome(String produtoNome) {
		this.produtoNome = produtoNome;
	}

	public int getQde() {
		return qde;
	}

	public void setQde(int qde) {
		this.qde = qde;
	}

	public BigDecimal getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(BigDecimal precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	public BigDecimal getPrecoTotal() {
		return precoTotal;
	}

	public void setPrecoTotal(BigDecimal precoTotal) {
		this.precoTotal = precoTotal;
	} 
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.produtoNome+"de qde :"+this.qde+"e PU :"+this.precoUnitario+"tem o PT :"+this.precoTotal;
	}
	
	

}
