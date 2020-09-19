package com.algaworks.algafood.apiexterno.categoriaClient;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoriaClientModelDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public CategoriaClientModel toDomainObject(CategoriaClientInput categoriaInput) {
		return modelMapper.map(categoriaInput, CategoriaClientModel.class);
	}

}
