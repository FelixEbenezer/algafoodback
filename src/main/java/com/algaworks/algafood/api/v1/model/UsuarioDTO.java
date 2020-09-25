package com.algaworks.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;

public class UsuarioDTO extends RepresentationModel<UsuarioDTO> {

	private Long id; 
	
	private String nome;
	
//	private String nomeEmail; 
	
	private String email;
	
	
	
/*
	public String getNomeEmail() {
		return nomeEmail;
	}

	public void setNomeEmail(String nomeEmail) {
		this.nomeEmail = nomeEmail;
	}
*/
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
	 return nome; 
	//	return nome + email;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	} 
	
	
}
