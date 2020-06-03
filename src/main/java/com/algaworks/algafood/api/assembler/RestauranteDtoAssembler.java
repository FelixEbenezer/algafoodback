package com.algaworks.algafood.api.assembler;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.model.RestauranteDTO;
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
		restauranteDTO.getEndereco().getCidade().add(algaLinks.linkToCidadeSelf(restauranteDTO.getEndereco().getCidade().getId()));
		
		restauranteDTO.getFormasPagamento().forEach(item -> {
			item.add(algaLinks.linkToFormaPagamentoSelf(restauranteDTO.getId(), item.getId()));
		});
		
		restauranteDTO.add(algaLinks.linkToFormaPagamentoRel(restauranteDTO.getId()));
		
		restauranteDTO.add(algaLinks.linkToRestauranteResponsaveis(restauranteDTO.getId()));
		
					
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
