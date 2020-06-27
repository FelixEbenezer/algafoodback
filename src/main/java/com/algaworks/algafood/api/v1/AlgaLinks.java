package com.algaworks.algafood.api.v1;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.controller.CidadeController;
import com.algaworks.algafood.api.v1.controller.CozinhaController;
import com.algaworks.algafood.api.v1.controller.FormaPagamentoController;
import com.algaworks.algafood.api.v1.controller.GrupoController;
import com.algaworks.algafood.api.v1.controller.GrupoPermissaoController;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.controller.PermissaoController;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.controller.RestauranteFormaPagamentoController;
import com.algaworks.algafood.api.v1.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.v1.controller.RestauranteProdutoFotoController;
import com.algaworks.algafood.api.v1.controller.RestauranteUsuarioResponsavelController;
import com.algaworks.algafood.api.v1.controller.UsuarioController;
import com.algaworks.algafood.api.v1.controller.UsuarioGrupoController;
import com.algaworks.algafood.api.v1.vendaDiariaApi.EstatisticasController;


@Component
public class AlgaLinks {
	
   public static final	TemplateVariables PAGES_VARIABLES = new TemplateVariables(
			new TemplateVariable("page", VariableType.REQUEST_PARAM),
			new TemplateVariable("size", VariableType.REQUEST_PARAM),
			new TemplateVariable("sort", VariableType.REQUEST_PARAM));
   
   public static final TemplateVariables PROJECAO_VARIABLES = new TemplateVariables(
			new TemplateVariable("projecao", VariableType.REQUEST_PARAM)); 


	
	public Link linkToPedidos() {
				
		TemplateVariables filtroPedido = new TemplateVariables(
				new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacacaoInicio", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacacaoFim", VariableType.REQUEST_PARAM));
		
		
		String pedidosUrl = WebMvcLinkBuilder.linkTo(PedidoController.class).toUri().toString();
		
		return new Link(UriTemplate.of(pedidosUrl,(PAGES_VARIABLES.concat(filtroPedido))), "pedidos");
	}
	
	//link para o metodo listar com total-geral
		
	public Link linkToPedidosTotal() {
	//	TemplateVariables filtroTotalGeral = new TemplateVariables(
	//			new TemplateVariable("total-geral", VariableType.REQUEST_PARAM));

		String doPedido = linkToPedidos().toString()+ "/total-geral";
		return new Link(UriTemplate.of(doPedido), "total-geral").withRel("total-geral");
		
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
	
/*	public Link linkToRestaurantes() {
	    return WebMvcLinkBuilder.linkTo(RestauranteController.class).withRel("restaurantes");
	}*/
	
	public Link linkToRestaurantes() {
	    String restaurantesUrl = WebMvcLinkBuilder.linkTo(RestauranteController.class).toUri().toString();
	    
	    return new Link(UriTemplate.of(restaurantesUrl, PROJECAO_VARIABLES), "restaurantes");
	}


	public Link linkToRestaurantesSelf() {
	    return WebMvcLinkBuilder.linkTo(RestauranteController.class).withSelfRel();
	}

	//links para cozinhas
	
	public Link linkToCozinhas(String rel) {
	    return WebMvcLinkBuilder.linkTo(CozinhaController.class).withRel(rel);
	}
	public Link linkToCozinha(Long cozinhaId) {
	    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CozinhaController.class)
	            .buscar(cozinhaId)).withRel("cozinhas");
	}

	public Link linkToCozinhaSelf(Long cozinhaId) {
	    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CozinhaController.class)
	            .buscar(cozinhaId)).withSelfRel();
	}
	
	
	// links para cidades
	
	public Link linkToCidades(String rel) {
	    return WebMvcLinkBuilder.linkTo((CidadeController.class)).withRel(rel);
	}

	public Link linkToCidadeSelf() {
	    return linkToCidades(IanaLinkRelations.SELF.value());
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
	
	
	
	//LINKS para ativar/inativar e abrir/fechar restaurantes:
	//Vamos adicionar métodos para criação dos links necessários.

	public Link linkToRestauranteAbertura(Long restauranteId, String rel) {
	    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class)
	            .abrir(restauranteId)).withRel(rel);
	}

	public Link linkToRestauranteFechamento(Long restauranteId, String rel) {
	    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class)
	            .fechar(restauranteId)).withRel(rel);
	}

	public Link linkToRestauranteInativacao(Long restauranteId, String rel) {
	    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class)
	            .inativarRestaurante(restauranteId)).withRel(rel);
	}

	public Link linkToRestauranteAtivacao(Long restauranteId, String rel) {
	    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class)
	            .ativarRestaurante(restauranteId)).withRel(rel);
	}
	
	//links para forma de pagamento
	public Link linkToRestauranteFormasPagamento(Long restauranteId) {
	    return linkToFormaPagamentoRel(restauranteId);
	}

	public Link linkToFormasPagamento(String rel) {
	    return WebMvcLinkBuilder.linkTo(FormaPagamentoController.class).withRel(rel);
	}

	public Link linkToFormasPagamento() {
	    return linkToFormasPagamento(IanaLinkRelations.SELF.value());
	} 
	
	//link para dissociar/associar formaPagamentos ao restaurante
	public Link linkToRestauranteFormasPagamentoDissociar(Long restauranteId, Long formaPagamentoId) {
	    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteFormaPagamentoController.class).dissociar(restauranteId, formaPagamentoId))
	    		.withRel("associar");
	}
	
	public Link linkToRestauranteFormasPagamentoAssociar(Long restauranteId) {
	    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteFormaPagamentoController.class)
	    		.associar(restauranteId, null))
	    		.withRel("dissociar");
	}
	
	//links para associar/dissociar responsaveis/clientes ao determinado restaurante
	public Link linkToRestauranteResponsavelDissociar(Long restauranteId, Long usuarioId) {

		    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteUsuarioResponsavelController.class)
		            .dissociarRes(restauranteId, usuarioId)).withRel("dissociar");
		}

		public Link linkToRestauranteResponsavelAssociacao(Long restauranteId) {
		    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteUsuarioResponsavelController.class)
		            .associarRes(restauranteId, null)).withRel("associar");
		}
		
	//links para produtos por restaurantes
		
		public Link linkToProdutos(Long restauranteId, String rel) {
		    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteProdutoController.class)
		            .listar(restauranteId, null)).withRel(rel);
		}

		public Link linkToProdutos(Long restauranteId) {
		    return linkToProdutos(restauranteId, IanaLinkRelations.SELF.value());
		}
		
		
				
	//links para foto de produto de restaurante
		public Link linkToFotoProduto(Long restauranteId, Long produtoId, String rel) {
		    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteProdutoFotoController.class)
		            .buscar(restauranteId, produtoId)).withRel(rel);
		}

		public Link linkToFotoProduto(Long restauranteId, Long produtoId) {
		    return linkToFotoProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
		}

		
	//links para grupos
		public Link linkToGrupos(String rel) {
		    return WebMvcLinkBuilder.linkTo(GrupoController.class).withRel(rel);
		}

		public Link linkToGrupos() {
		    return linkToGrupos(IanaLinkRelations.SELF.value());
		}

		public Link linkToGrupoPermissoes(Long grupoId, String rel) {
		    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GrupoPermissaoController.class)
		    		.listar(grupoId)).withRel(rel);
		} 
		
	
	//links para permissao e grupopermissao
		public Link linkToPermissoes(String rel) {
		    return WebMvcLinkBuilder.linkTo(PermissaoController.class).withRel(rel);
		}

		public Link linkToPermissoes() {
		    return linkToPermissoes(IanaLinkRelations.SELF.value());
		}

		public Link linkToGrupoPermissoes(Long grupoId) {
		    return linkToGrupoPermissoes(grupoId, IanaLinkRelations.SELF.value());
		}

		public Link linkToGrupoPermissaoAssociacao(Long grupoId, String rel) {
		    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GrupoPermissaoController.class)
		            .associar(grupoId, null)).withRel(rel);
		}

		public Link linkToGrupoPermissaoDesassociacao(Long grupoId, Long permissaoId, String rel) {
		    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GrupoPermissaoController.class)
		            .dissociar(grupoId, permissaoId)).withRel(rel);
		}
		
		//links de associar e dissociars usuarios a grupos
		public Link linkToUsuarioGrupoAssociacao(Long usuarioId, String rel) {
		    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioGrupoController.class)
		            .associarGrupo(usuarioId, null)).withRel(rel);
		}

		public Link linkToUsuarioGrupoDesassociacao(Long usuarioId, Long grupoId, String rel) {
		    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioGrupoController.class)
		            .dissociarGrupo(usuarioId, grupoId)).withRel(rel);
		}
		
		//links para estatisticas
		
		public Link linkToEstatisticas(String rel) {
		    return WebMvcLinkBuilder.linkTo(EstatisticasController.class).withRel(rel);
		}

		public Link linkToEstatisticasVendasDiarias(String rel) {
		    TemplateVariables filtroVariables = new TemplateVariables(
		            new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
		            new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
		            new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM),
		            new TemplateVariable("timeOffset", VariableType.REQUEST_PARAM));
		    
		    String pedidosUrl = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstatisticasController.class)
		    		.consultarVendasDiariasPdf(null, null)).toUri().toString()+"/vendas-diarias";
		    
		    return new Link(UriTemplate.of(pedidosUrl, filtroVariables), rel);
		}   

}
