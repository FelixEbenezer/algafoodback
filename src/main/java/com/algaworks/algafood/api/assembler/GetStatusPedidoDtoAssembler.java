package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.GetStatusPedidoDTO;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class GetStatusPedidoDtoAssembler {

	@Autowired
	private ModelMapper modelMapper; 
	
	public GetStatusPedidoDTO toDTO (Pedido pedido) {
		return modelMapper.map(pedido, GetStatusPedidoDTO.class);
	}
}
