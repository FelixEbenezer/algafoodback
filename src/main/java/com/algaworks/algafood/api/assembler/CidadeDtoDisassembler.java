package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.CidadeInputDTO;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class CidadeDtoDisassembler {

	@Autowired
	private ModelMapper modelMapper; 
	
	public Cidade toDomainObject(CidadeInputDTO cidadeInputDTO) {
		return modelMapper.map(cidadeInputDTO, Cidade.class);
	}
	
	public void copyToDomainObject(CidadeInputDTO cidadeInputDTO, Cidade cidade) {
		cidade.setEstado(new Estado());
		modelMapper.map(cidadeInputDTO, cidade);
	}
}
