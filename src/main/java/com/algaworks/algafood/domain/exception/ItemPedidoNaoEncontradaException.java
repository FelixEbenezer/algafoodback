package com.algaworks.algafood.domain.exception;

public class ItemPedidoNaoEncontradaException extends EntidadeNaoEncontradaException {

	
	private static final long serialVersionUID = 1L;

	public ItemPedidoNaoEncontradaException(String msg) {
		super(msg);
	}
	
	public ItemPedidoNaoEncontradaException(Long id) {
		this(String.format("Não existe nenhum Item pedido com código %d", id));
	}

}
