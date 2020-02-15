package com.algaworks.algafood.domain.exception;

public class CozinhaEncontradaException extends EntidadeNaoEncontradaException {

	
	private static final long serialVersionUID = 1L;
	
	
	public CozinhaEncontradaException(String msg) {
		super(msg);
	}
	
	public CozinhaEncontradaException(Long cozinhaId) {
		this(String.format("Não existe um cadastro de cozinha com código %d", cozinhaId));
	}

}
