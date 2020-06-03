package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.controller.StatusPedidoController;
import com.algaworks.algafood.api.model.PedidoDTO;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoDtoAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoDTO> {

	@Autowired
	private ModelMapper modelMapper; 
	
	@Autowired
	private AlgaLinks algaLinks; 
	
	public PedidoDtoAssembler() {
		super(PedidoController.class, PedidoDTO.class);
	}
	
	@Override
	public PedidoDTO toModel(Pedido pedido) {
		PedidoDTO pedidoDTO = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoDTO);
		
		

	/*	TemplateVariables pageVariables = new TemplateVariables(
				new TemplateVariable("page", VariableType.REQUEST_PARAM),
				new TemplateVariable("size", VariableType.REQUEST_PARAM),
				new TemplateVariable("sort", VariableType.REQUEST_PARAM));
		
		
		TemplateVariables filtroPedido = new TemplateVariables(
				new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacacaoInicio", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacacaoFim", VariableType.REQUEST_PARAM));
		
		
		String pedidosUrl = WebMvcLinkBuilder.linkTo(PedidoController.class).toUri().toString(); 
		
		pedidoDTO.add(new Link(UriTemplate.of(pedidosUrl,(pageVariables.concat(filtroPedido))), "pedidos"));*/
		
		
		pedidoDTO.add(algaLinks.linkToPedidos());
		
	//	pedidoDTO.add(algaLinks.linkToPedidos().withRel("total-geral"));
	//	pedidoDTO.add(algaLinks.linkToPedidosTotal().withSelfRel("total-geral"));
		
	//	pedidoDTO.add(WebMvcLinkBuilder.linkTo(PedidoController.class).withRel("total-geral"));
		
	
		
		//link to getCliente - usuarioController
	/*	pedidoDTO.getCliente().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class)
				              .buscarPorId(pedido.getCliente().getId())).withSelfRel()); */
		
		pedidoDTO.getCliente().add(algaLinks.linkToPedidos(pedidoDTO.getCliente().getId()));
		
		
		
		//link to getEntrecoEntrega() - endereco-cidade
	/*	pedidoDTO.getEnderecoEntrega().getCidade().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class)
	              .buscarPorId(pedido.getEndereco().getCidade().getId())).withSelfRel()); */
		
	//	pedidoDTO.getEnderecoEntrega().add(algaLinks.linkToPedidosEndereco(pedido));
		pedidoDTO.getEnderecoEntrega().add(algaLinks.linkToPedidosEndereco(pedidoDTO.getEnderecoEntrega().getCidade().getId()));
		
	
		
		//links para o status de pedido
		
		if (pedido.podeSerConfirmado()) {
			// pedidoModel.add(algaLinks.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
			pedidoDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StatusPedidoController.class)
					.confirmar(pedido.getCodigo())).withSelfRel());
		}
		
		if (pedido.podeSerCancelado()) {
		//	pedidoModel.add(algaLinks.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
			pedidoDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StatusPedidoController.class)
					.cancelar(pedido.getCodigo())).withSelfRel());
		}
		
		if (pedido.podeSerEntregue()) {
		//	pedidoModel.add(algaLinks.linkToEntregaPedido(pedido.getCodigo(), "entregar"));
			pedidoDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StatusPedidoController.class)
					.entregar(pedido.getCodigo())).withSelfRel());
		}
				

		
		
		//link para FOrmaPagamento 
		pedidoDTO.getFormaPagamento().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FormaPagamentoController.class)
	              .buscarPorId(pedido.getFormaPagamento().getId())).withSelfRel());
		
	// com ItemPedido é uma lista dentro do Pedido, devemos percorrer lo via forEach, e para cada um dos itens, atribuimos um link
	// dentro do qual tratamos cada item como um RepresentationModel visto que ItemPedido extend a RepresentationModel, logo podemos
	// chamar o metodo add, algo que não era possivel com getItens, e depois para buscarProdutoPor restaurante, chamamos o metodo 
	// listarProdutoPorRestaurante que recebe 2 parametros: os ids do produto e do produto
		pedidoDTO.getItens().forEach(item -> {
            item.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteProdutoController.class)
                    .listarProdutoPorRestaurante(pedidoDTO.getRestaurante().getId(), item.getProdutoId()))
                    .withRel("produto"));
        });
		
		return pedidoDTO;
	}
	
	@Override
	public CollectionModel<PedidoDTO> toCollectionModel(Iterable<? extends Pedido> entities) {
		// TODO Auto-generated method stub
		return super.toCollectionModel(entities)
					.add(WebMvcLinkBuilder.linkTo(PedidoController.class).withSelfRel());
	}
	
	
	/*
	public PedidoDTO toDTO (Pedido pedido) {
		return modelMapper.map(pedido, PedidoDTO.class);
	}
	
	public List<PedidoDTO> toCollectionObject(List<Pedido> pedidos){
		return pedidos.stream().map(pedido -> toDTO(pedido))
				.collect(Collectors.toList());
	}*/
	
	
}
