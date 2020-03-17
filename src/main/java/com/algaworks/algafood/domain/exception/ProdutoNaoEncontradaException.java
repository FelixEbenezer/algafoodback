package com.algaworks.algafood.domain.exception;

public class ProdutoNaoEncontradaException extends EntidadeNaoEncontradaException {

	
	private static final long serialVersionUID = 1L;

	public ProdutoNaoEncontradaException(String msg) {
		super(msg);
	}
	
	public ProdutoNaoEncontradaException(Long restauranteId, Long produtoId) {
		this(String.format("Não existe nenhum produto com código %d no restaurante %d", restauranteId, produtoId));
	}

}
