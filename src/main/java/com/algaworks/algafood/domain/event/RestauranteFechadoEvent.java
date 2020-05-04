package com.algaworks.algafood.domain.event;

import com.algaworks.algafood.domain.model.Restaurante;

public class RestauranteFechadoEvent {
	
	private Restaurante restaurante;

	public RestauranteFechadoEvent(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	} 
	
	
	
	

}
