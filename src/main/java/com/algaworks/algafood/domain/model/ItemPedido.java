package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class ItemPedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	@Column(nullable = false)
	private Integer quantidade; 
	
	@Column(nullable = false)
	private BigDecimal precoUnitario;
	
	@Column(nullable = false)
	private BigDecimal precoTotal;
	
	private String observacao; 
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Produto produto; 
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Pedido pedido;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
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

	public String getobservacao() {
		return observacao;
	}

	public void setobservacao(String observacao) {
		this.observacao = observacao;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemPedido other = (ItemPedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	} 
	
	public void calcularPrecoTotal() {
	    BigDecimal precoUnitario = this.getPrecoUnitario();
	    Integer quantidade = this.getQuantidade();

	    if (precoUnitario == null) {
	        precoUnitario = BigDecimal.ZERO;
	    }

	    if (quantidade == null) {
	        quantidade = 0;
	    }

	    this.setPrecoTotal(precoUnitario.multiply(new BigDecimal(quantidade)));
	}
	
	

}
