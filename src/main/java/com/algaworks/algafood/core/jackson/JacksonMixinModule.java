package com.algaworks.algafood.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;


public class JacksonMixinModule extends SimpleModule {
	
	private static final long serialVersionUID = 1L;
	
	public JacksonMixinModule() {
	//	setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
		// setMixInAnnotation(Cidade.class, CidadeMixin.class);
		// setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
	}

}
