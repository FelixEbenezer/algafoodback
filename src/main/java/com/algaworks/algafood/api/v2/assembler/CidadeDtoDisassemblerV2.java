package com.algaworks.algafood.api.v2.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.algaworks.algafood.api.v2.model.input.CidadeInputDTOV2;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class CidadeDtoDisassemblerV2 {

	@Autowired
	private ModelMapper modelMapper; 
	
	public Cidade toDomainObject(CidadeInputDTOV2 cidadeInputDTO) {
		return modelMapper.map(cidadeInputDTO, Cidade.class);
	}
	
	public void copyToDomainObject(CidadeInputDTOV2 cidadeInputDTO, Cidade cidade) {
		cidade.setEstado(new Estado());
		modelMapper.map(cidadeInputDTO, cidade);
	}
}
