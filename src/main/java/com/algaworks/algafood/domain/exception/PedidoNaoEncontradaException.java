package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradaException extends EntidadeNaoEncontradaException {

	
	private static final long serialVersionUID = 1L;

	public PedidoNaoEncontradaException(String msg) {
		super(msg);
	}
	
	public PedidoNaoEncontradaException(Long id) {
		this(String.format("Não existe nenhum pedido com código %d", id));
	}

}
