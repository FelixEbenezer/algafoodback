package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.Valid;

import org.hibernate.annotations.CreationTimestamp;

import com.algaworks.algafood.domain.exception.NegocioException;

@Entity
@Table
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String codigo;
	
	private BigDecimal subtotal;
	
	@Column(name = "tx_frete")
	private BigDecimal taxaFrete;
	
	private BigDecimal valorTotal;
	
	@CreationTimestamp
	@Column(nullable = false)
	private OffsetDateTime dataCriacao;
	
	private OffsetDateTime dataConfirmacao; 
	
	private OffsetDateTime dataCancelamento;
	
	private OffsetDateTime dataEntrega; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private FormaPagamento formaPagamento; 
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Restaurante restaurante;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_cliente_id", nullable = false)
	private Usuario cliente; 
	
	@Enumerated(EnumType.STRING)
	private StatusPedido status = StatusPedido.CRIADO; 
	
	@Valid
	@Embedded
	private Endereco endereco;
	
	// claro que é uma lista de itemPedido dentro do Pedido
	// a outra relação é feita dentro da table Pedido
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itens = new ArrayList<>();

	
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getSubTotal() {
		return subtotal;
	}

	public void setSubTotal(BigDecimal subtotal) {
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

	public OffsetDateTime getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(OffsetDateTime dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public OffsetDateTime getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(OffsetDateTime dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

	public Usuario getCliente() {
		return cliente;
	}

	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}

	public StatusPedido getStatus() {
		return status;
	}

	//definir este metodo em baixo
	/*
	public void setStatus(StatusPedido status) {
		this.status = status;
	}
*/
	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
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
		Pedido other = (Pedido) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		return true;
	} 
	
/*	public void calcularValorTotal() {
	    this.subtotal = getItens().stream()
	        .map(item -> item.getPrecoTotal())
	        .reduce(BigDecimal.ZERO, BigDecimal::add);
	    
	    this.valorTotal = this.subtotal.add(this.taxaFrete);
	}*/
	
	// aqui neste metodo juntamos o metodo calcularValorTotal acima com os 
	// os outros metodos definirFrete e atribuirPedidoAosItens
	public void calcularValorTotal() {
	    getItens().forEach(ItemPedido::calcularPrecoTotal);
	    
	    this.subtotal = getItens().stream()
	        .map(item -> item.getPrecoTotal())
	        .reduce(BigDecimal.ZERO, BigDecimal::add);
	    
	    this.valorTotal = this.subtotal.add(this.taxaFrete);
	}
	
	/*public void definirFrete() {
		 setTaxaFrete(getRestaurante().getTaxaFrete());
		}
	
	public void atribuirPedidoAosItens() {
		 getItens().forEach(item -> item.setPedido(this));
		}
	*/
	
	// alteracoes de status do pedido
	
	//1. refefinimos o metodo setStatus para ser chamado somente dentro dos 
	// metodos confirmar, cancelar e entregar que vamos criar logo a seguir
	// e neste metodo definimos tbem a validacao caso nao lancar o NegocioException
	
	private void setStatus(StatusPedido novoStatus) {
		
		 if(getStatus().naoPodeAlterarPara(novoStatus)) {
		 throw new NegocioException(String.format("Status de pedido %d não pode ser alterado de %s para %s",
						getCodigo(), getStatus().getDescricao(), novoStatus.getDescricao()));  
		}
		this.status = novoStatus; 
		}

	//2. criaçao de metodos de alteracaoes de status
	
	    public void confirmar() {  // visto e
		 setStatus(StatusPedido.CONFIRMADO);
		 setDataConfirmacao(OffsetDateTime.now());
		}
	    
	    public void entregar() {  // visto e
	    	 setStatus(StatusPedido.ENTREGUE);
	    	 setDataConfirmacao(OffsetDateTime.now());
	    	}
	    
	    public void cancelar() {  // visto e
	    	 setStatus(StatusPedido.CANCELADO);
	    	 setDataConfirmacao(OffsetDateTime.now());
	    	}


	    // UUIID
	    @PrePersist
	      private void gerarCodigo() {
	    	 //setCodigo(UUID.randomUUID().toString()); 
	    	 setCodigo(geradorChar());
	    	}
	    
	    public String geradorChar(){
              
	    	   final Random gerador = new Random();
	    	   // String codigo = "LDA";
	    	   String codigo = getEndereco().getCep();
	    			
	    	   for(int i=0; i<3; i++) {
	    	       codigo = codigo.concat(String.valueOf('a'+Math.abs(gerador.nextInt(26))));
	    	   }

	    	   return codigo;
	    	}


	

}
