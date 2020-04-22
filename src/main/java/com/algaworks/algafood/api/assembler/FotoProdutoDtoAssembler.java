package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.FotoProdutoDTO;
import com.algaworks.algafood.domain.model.FotoProduto;

@Component
public class FotoProdutoDtoAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public FotoProdutoDTO toModel(FotoProduto foto) {
		return modelMapper.map(foto, FotoProdutoDTO.class);
	}
}
