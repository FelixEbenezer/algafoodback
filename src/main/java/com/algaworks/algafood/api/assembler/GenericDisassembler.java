package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenericDisassembler<T, S> {

	@Autowired
	private ModelMapper modelMapper; 
	
	public S toDomain(T originInput, Class<S> type) {
        return modelMapper.map(originInput, type);
    }

    public void copyToDomainObject(T originInput, Object destination) {
        modelMapper.map(originInput, destination);
    }
}
