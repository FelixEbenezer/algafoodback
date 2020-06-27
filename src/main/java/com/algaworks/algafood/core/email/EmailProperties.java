package com.algaworks.algafood.core.email;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;


@Validated
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {

	//@NotNull
	private String remetente;
	
	private String destinatario;
	
	// Atribuimos FAKE como padrão
	// Isso evita o problema de enviar e-mails de verdade caso você esqueça
	// de definir a propriedade
	private Implementacao impl = Implementacao.FAKE;

	public enum Implementacao {
	    SMTP, FAKE
	}
	
	
	
	

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public Implementacao getImpl() {
		return impl;
	}

	public void setImpl(Implementacao impl) {
		this.impl = impl;
	}

	public String getRemetente() {
		return remetente;
	}

	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}
	
	
	
}