package com.algaworks.algafood.api.model;

public class CidadeResumoDTO {
	
	private Long id; 
	
	private String nome; 
	
	private String nomeEstado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEstado() {
		return nomeEstado;
	}

	public void setEstado(String nomeEstado) {
		this.nomeEstado = nomeEstado;
	} 
	

}
