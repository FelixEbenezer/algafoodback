package com.algaworks.algafood.api.v1.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.ItemPedidoDTO;
import com.algaworks.algafood.domain.model.ItemPedido;

@Component
public class ItemPedidoDtoAssembler {

	@Autowired
	private ModelMapper modelMapper; 
	
	public ItemPedidoDTO toDTO(ItemPedido itemPedido) {
		return modelMapper.map(itemPedido, ItemPedidoDTO.class);
	}
	
	public List<ItemPedidoDTO> toCollectionObject(Collection<ItemPedido> itens){
		return itens.stream().map(item -> toDTO(item))
				.collect(Collectors.toList());
	}
}
