package com.algaworks.algafood.api.v1.assembler;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.controller.UsuarioController;
import com.algaworks.algafood.api.v1.model.PedidoResumoDTO;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoResumoDtoAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoDTO> {

	@Autowired
	private ModelMapper modelMapper; 
	
	@Autowired
	private AlgaLinks algaLinks; 
	
	public PedidoResumoDtoAssembler() {
		super(PedidoController.class, PedidoResumoDTO.class);
	}
	
	@Override
	public PedidoResumoDTO toModel(Pedido pedido) {
		PedidoResumoDTO pedidoResumoDto = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoResumoDto); 
		
		//adicionar o link da collection sem recorrer ao metodo listar que retorne um page
		pedidoResumoDto.add(WebMvcLinkBuilder.linkTo(PedidoController.class).withRel("pedidos"));
		
		//adicionar o link para o restauranteResumoDto do recurso restaurante
		// e devo usar o methodOn para ir buscar o metodo buscarPorId 
		pedidoResumoDto.getRestaurante().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class)
				.buscarPorId(pedido.getRestaurante().getId())).withSelfRel());
		
		//adicionar o link para ir buscar o Usuario como recurso do cliente como 
		//fizemos com o restaurante
		pedidoResumoDto.getCliente().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class)
				.buscarPorId(pedido.getCliente().getId())).withSelfRel());
		
		
		//o link para metodo listar com total-geral
		pedidoResumoDto.add(algaLinks.linkToPedidosTotal());
				
		return pedidoResumoDto; 
	}
	
	
	@Override
	public CollectionModel<PedidoResumoDTO> toCollectionModel(Iterable<? extends Pedido> entities) {
		// TODO Auto-generated method stub
		return super.toCollectionModel(entities)
					.add(WebMvcLinkBuilder.linkTo(PedidoController.class).withSelfRel());
	}
	
	/*
	public PedidoResumoDTO toDTO (Pedido pedido) {
		return modelMapper.map(pedido, PedidoResumoDTO.class);
	}
	
	public List<PedidoResumoDTO> toCollectionObject(List<Pedido> pedidos){
		return pedidos.stream().map(pedido -> toDTO(pedido))
				.collect(Collectors.toList());
	}
	*/
	
}
