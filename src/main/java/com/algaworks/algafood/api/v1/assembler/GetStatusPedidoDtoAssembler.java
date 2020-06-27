package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.GetStatusPedidoDTO;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class GetStatusPedidoDtoAssembler {

	@Autowired
	private ModelMapper modelMapper; 
	
	public GetStatusPedidoDTO toDTO (Pedido pedido) {
		return modelMapper.map(pedido, GetStatusPedidoDTO.class);
	}
}


/*

@Component
public class GetStatusPedidoDtoAssembler extends RepresentationModelAssemblerSupport<StatusPedido, GetStatusPedidoDTO> {

	@Autowired
	private ModelMapper modelMapper; 
	
	public GetStatusPedidoDtoAssembler() {
		super(StatusPedidoController.class, GetStatusPedidoDTO.class);
	}
	
	@Override
	public GetStatusPedidoDTO toModel(Pedido pedido) {
		GetStatusPedidoDTO getStatusDTO = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, getStatusDTO);
		
		return getStatusDTO;
	}
	

//	public GetStatusPedidoDTO toDTO (Pedido pedido) {
//		return modelMapper.map(pedido, GetStatusPedidoDTO.class);
	}
}

*/