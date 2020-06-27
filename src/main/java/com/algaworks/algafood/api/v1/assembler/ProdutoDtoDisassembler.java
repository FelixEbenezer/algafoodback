package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.input.ProdutoInputDTO;
import com.algaworks.algafood.domain.model.Produto;

@Component
public class ProdutoDtoDisassembler {

	@Autowired
	private ModelMapper modelMapper; 
	
	public Produto toDomainObject (ProdutoInputDTO produtoInputDTO) {
		return modelMapper.map(produtoInputDTO, Produto.class);
	}
	
	public void copyToDomainObject(ProdutoInputDTO produtoInputDTO, Produto produto) {
		// produto.setRestaurante(new Restaurante());
		modelMapper.map(produtoInputDTO, produto);
	}
}
