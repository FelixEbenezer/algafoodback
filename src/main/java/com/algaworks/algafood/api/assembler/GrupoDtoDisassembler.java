package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.GrupoInputDTO;
import com.algaworks.algafood.domain.model.Grupo;

@Component
public class GrupoDtoDisassembler {

	@Autowired
	private ModelMapper modelMapper; 
	
	public Grupo toDomainObject (GrupoInputDTO grupoInputDTO) {
		return modelMapper.map(grupoInputDTO, Grupo.class);
	}
	
	
	public void copyToDomainObject(GrupoInputDTO grupoInputDTO, Grupo grupo) {
		modelMapper.map(grupoInputDTO, grupo);
	}
}
