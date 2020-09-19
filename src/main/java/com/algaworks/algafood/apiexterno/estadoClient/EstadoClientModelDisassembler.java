package com.algaworks.algafood.apiexterno.estadoClient;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.Estado;

@Component
public class EstadoClientModelDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Estado toDomainObject(EstadoClientModel estadoClientModel) {
		return modelMapper.map(estadoClientModel, Estado.class);
	}
	
}