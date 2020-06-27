package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.input.EstadoInputDTO;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class EstadoDtoDisassembler {

	@Autowired
	private ModelMapper modelMapper; 
	
	public Estado toDomainObject(EstadoInputDTO esatdoInputDTO) {
		return modelMapper.map(esatdoInputDTO, Estado.class);
	}
	
	public void copyToDomainObject (EstadoInputDTO estadoInputDTO, Estado estado) {
		modelMapper.map(estadoInputDTO, estado);
	}
}
