package com.algaworks.algafood.api;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.controller.RestauranteFormaPagamentoController;
import com.algaworks.algafood.api.controller.RestauranteUsuarioResponsavelController;
import com.algaworks.algafood.api.controller.UsuarioController;


@Component
public class AlgaLinks {
	
   public static final	TemplateVariables PAGES_VARIABLES = new TemplateVariables(
			new TemplateVariable("page", VariableType.REQUEST_PARAM),
			new TemplateVariable("size", VariableType.REQUEST_PARAM),
			new TemplateVariable("sort", VariableType.REQUEST_PARAM));

	
	public Link linkToPedidos() {
				
		TemplateVariables filtroPedido = new TemplateVariables(
				new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacacaoInicio", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacacaoFim", VariableType.REQUEST_PARAM));
		
		
		String pedidosUrl = WebMvcLinkBuilder.linkTo(PedidoController.class).toUri().toString();
		
		return new Link(UriTemplate.of(pedidosUrl,(PAGES_VARIABLES.concat(filtroPedido))), "pedidos");
	}
	
	public Link linkToPedidosTotal() {
		TemplateVariables filtroTotalGeral = new TemplateVariables(
				new TemplateVariable("pes=total-geral", VariableType.REQUEST_PARAM));

		String doPedido = linkToPedidos().toString();
		return new Link(UriTemplate.of(doPedido,(filtroTotalGeral)), "total-geral");
	}
	
	//LINK DE CLIENTEID
/*	public Link linkToPedidos(Pedido pedido) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class)
	              .buscarPorId(pedido.getCliente().getId())).withSelfRel();
	}*/
	
	public Link linkToPedidos(Long clienteId) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class)
	              .buscarPorId(clienteId)).withSelfRel();
	}
	
	
	//LINK PARA ENDERECO 
/*	public Link linkToPedidosEndereco (Pedido pedido) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class)
	              .buscarPorId(pedido.getEndereco().getCidade().getId())).withSelfRel();
	}*/
	
	public Link linkToPedidosEndereco (Long cidadeId) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class)
	              .buscarPorId(cidadeId)).withSelfRel();
	}
	
	//links de restaurantes
	
	public Link linkToRestaurantes() {
	    return WebMvcLinkBuilder.linkTo(RestauranteController.class).withRel("restaurantes");
	}

	public Link linkToRestaurantesSelf() {
	    return WebMvcLinkBuilder.linkTo(RestauranteController.class).withSelfRel();
	}

	
	public Link linkToCozinha(Long cozinhaId) {
	    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CozinhaController.class)
	            .buscar(cozinhaId)).withRel("cozinhas");
	}

	public Link linkToCozinhaSelf(Long cozinhaId) {
	    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CozinhaController.class)
	            .buscar(cozinhaId)).withSelfRel();
	}
	
	public Link linkToCidadeSelf(Long cidadeId) {
	    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class)
	            .buscarPorId(cidadeId)).withSelfRel();
	}
	
	//adicionamos sempre 2 links para cada entidade para defefinir o self e o rel
	//Links self e rel para as formas de pagamentos
	public Link linkToFormaPagamentoSelf(Long restauranteId, Long formaPagamentoId) {
	    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteFormaPagamentoController.class)
	    		.associar(restauranteId, formaPagamentoId)).withSelfRel();
	}
	
	public Link linkToFormaPagamentoRel(Long restauranteId) {
	    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteFormaPagamentoController.class)
	    		.listar(restauranteId)).withRel("formas-pagamento");
	}
	
	//link para adicionar o responsavel do usuarios dentro duma link de coleção mesmo se o usuario não faz parte 
	// do restauranteDTO, mas como ele consta no modelo restaurante, então existe um mapeamento entre ele logo, é possivel
	public Link linkToRestauranteResponsaveis(Long restauranteId) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteUsuarioResponsavelController.class)
	    		.listar(restauranteId)).withRel("responsaveis");
	}

}
