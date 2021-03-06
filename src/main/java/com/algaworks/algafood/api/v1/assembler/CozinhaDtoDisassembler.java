package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.input.CozinhaInputDTO;
import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaDtoDisassembler {

	@Autowired
	private ModelMapper modelMapper; 
	
	public Cozinha toDomainObject (CozinhaInputDTO cozinhaInputDto) {
		return modelMapper.map(cozinhaInputDto, Cozinha.class);
	}
	
	public void copyToDomainObject(CozinhaInputDTO cozinhaInputDTO, Cozinha cozinha) {
		modelMapper.map(cozinhaInputDTO, cozinha);
		
	}
}
