package com.algaworks.algafood.domain.exception;

public class RestauranteNaoEncontradaException extends EntidadeNaoEncontradaException {

	
	private static final long serialVersionUID = 1L;
	
	
	public RestauranteNaoEncontradaException(String msg) {
		super(msg);
	}
	
	public RestauranteNaoEncontradaException(Long restauranteId) {
		this(String.format("Não existe um cadastro de restaurante com código %d", restauranteId));
	}

}
