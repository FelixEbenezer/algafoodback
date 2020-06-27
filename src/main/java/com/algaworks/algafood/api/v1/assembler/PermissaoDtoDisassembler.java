package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.input.PermissaoInputDTO;
import com.algaworks.algafood.domain.model.Permissao;

@Component
public class PermissaoDtoDisassembler {

	@Autowired
	private ModelMapper modelMapper; 
	
	public Permissao toDomainObject(PermissaoInputDTO permissaoInputDTO) {
		
		return modelMapper.map(permissaoInputDTO, Permissao.class);
	}
	
	public void copyToDomainObject(PermissaoInputDTO permissaoInputDTO, Permissao permissao) {
		modelMapper.map(permissaoInputDTO, permissao);
	}
}
