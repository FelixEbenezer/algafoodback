package com.algaworks.algafood.domain.exception;


// @ResponseStatus(HttpStatus.NOT_FOUND)
public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

	
	private static final long serialVersionUID = 1L;
	
	
	public CidadeNaoEncontradaException(String msg) {
		super(msg);
	}
	
	public CidadeNaoEncontradaException(Long cidadeId) {
		this(String.format("Não existe um cadastro de cidade com código %d", cidadeId));
	}

}
