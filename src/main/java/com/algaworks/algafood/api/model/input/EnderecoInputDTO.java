package com.algaworks.algafood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class EnderecoInputDTO {
	
	@NotNull
	private String cep;
	
	private String logradouro;
	
	@NotNull
	private String numero; 
	private String complemento;
	private String bairro;
	
	@NotNull
	@Valid
	private CidadeIdInput cidade;

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public CidadeIdInput getCidade() {
		return cidade;
	}

	public void setCidade(CidadeIdInput cidade) {
		this.cidade = cidade;
	}
	
	

}
