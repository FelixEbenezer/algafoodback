package com.algaworks.algafood.apiexterno.laposteClient;

import javax.validation.constraints.NotNull;

public class ColisClientInput {
	
		

	
	private String lang;
	
	private String scope; 
	
	@NotNull
	private String idShip;
	
	public ColisClientInput() {}

	
	
	public String getLang() {
		return lang;
	}



	public void setLang(String lang) {
		this.lang = lang;
	}



	public String getScope() {
		return scope;
	}



	public void setScope(String scope) {
		this.scope = scope;
	}



	public String getIdShip() {
		return idShip;
	}



	public void setIdShip(String idShip) {
		this.idShip = idShip;
	}



	


	
	
	

}
