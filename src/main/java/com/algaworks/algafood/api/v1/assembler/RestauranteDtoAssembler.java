package com.algaworks.algafood.api.v1.assembler;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.model.RestauranteDTO;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteDtoAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteDTO> {

	@Autowired
	private ModelMapper modelMapper; 
	
	@Autowired
	private AlgaLinks algaLinks; 
	
	public RestauranteDtoAssembler() {
		super(RestauranteController.class, RestauranteDTO.class);
		}
	
	@Override
	public RestauranteDTO toModel(Restaurante restaurante) {
		RestauranteDTO restauranteDTO = createModelWithId(restaurante.getId(), restaurante);
		modelMapper.map(restaurante, restauranteDTO);
		
		restauranteDTO.add(algaLinks.linkToRestaurantesSelf());
		restauranteDTO.add(algaLinks.linkToRestaurantes());
		restauranteDTO.getCozinha().add(algaLinks.linkToCozinha(restauranteDTO.getCozinha().getId()));
		
		
		// links para o id de ciade para evitar o null pointer 
		
		if (restauranteDTO.getEndereco() != null && restauranteDTO.getEndereco().getCidade() != null) {
			restauranteDTO.getEndereco().getCidade().add(algaLinks.linkToCidadeSelf(restauranteDTO.getEndereco().getCidade().getId()));
	    }
				
		restauranteDTO.getFormasPagamento().forEach(item -> {
			item.add(algaLinks.linkToFormaPagamentoSelf(restauranteDTO.getId(), item.getId()));
		});
		
		restauranteDTO.add(algaLinks.linkToFormaPagamentoRel(restauranteDTO.getId()));
		
		restauranteDTO.add(algaLinks.linkToRestauranteResponsaveis(restauranteDTO.getId()));
		
		//Links para abrir/fechar e ativar/inativar restaurantes
		if (restaurante.ativacaoPermitida()) {
			restauranteDTO.add(
					algaLinks.linkToRestauranteAtivacao(restaurante.getId(), "inativar"));
		}

		if (restaurante.inativacaoPermitida()) {
			restauranteDTO.add(
					algaLinks.linkToRestauranteInativacao(restaurante.getId(), "ativar"));
		}

		if (restaurante.aberturaPermitida()) {
			restauranteDTO.add(
					algaLinks.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
		}

		if (restaurante.fechamentoPermitido()) {
			restauranteDTO.add(
					algaLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
		}
		
		//links para produtos por restauantes
		restauranteDTO.add(algaLinks.linkToProdutos(restaurante.getId(), "produtos"));
	    
		
					
		return restauranteDTO; 
	}
	
	@Override
	public CollectionModel<RestauranteDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
		// TODO Auto-generated method stub
		return super.toCollectionModel(entities)
					.add(WebMvcLinkBuilder.linkTo(RestauranteController.class).withRel("restaurantes"));
	}
	
/*	public RestauranteDTO toDTO(Restaurante restaurante) {
		
		return modelMapper.map(restaurante, RestauranteDTO.class);
		
		/*CozinhaDTO cozinhaDTO = new CozinhaDTO();
		cozinhaDTO.setId(restaurante.getCozinha().getId());
		cozinhaDTO.setNome(restaurante.getCozinha().getNome());
		
		RestauranteDTO resDTO = new RestauranteDTO();
		resDTO.setId(restaurante.getId());
		resDTO.setNome(restaurante.getNome());
		resDTO.setTaxaFrete(restaurante.getTaxaFrete());
		resDTO.setCozinha(cozinhaDTO);
		return resDTO;*/
//	}
	/*
	public List<RestauranteDTO> toCollectionDTO(Collection<Restaurante> restaurantes) {
		 return restaurantes.stream()
			.map(restaurante -> toDTO(restaurante))
			.collect(Collectors.toList());
	}*/
	
}
