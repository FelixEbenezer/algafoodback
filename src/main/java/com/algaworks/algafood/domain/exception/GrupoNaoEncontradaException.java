package com.algaworks.algafood.domain.exception;

public class GrupoNaoEncontradaException extends EntidadeNaoEncontradaException {

	
	private static final long serialVersionUID = 1L;

	public GrupoNaoEncontradaException(String msg) {
		super(msg);
	}
	
	public GrupoNaoEncontradaException(Long id) {
		this(String.format("Não existe um cadastro de grupo com código %d", id));
	}

}
