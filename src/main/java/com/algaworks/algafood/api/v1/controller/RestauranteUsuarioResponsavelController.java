package com.algaworks.algafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.assembler.UsuarioDtoAssembler;
import com.algaworks.algafood.api.v1.model.UsuarioDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;

@RestController
@RequestMapping("/v1/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController {
	
	@Autowired
	private RestauranteService restauranteService; 
	
	@Autowired
	private UsuarioDtoAssembler assemblerUsuario; 
	
	@Autowired
	private AlgaLinks algaLinks; 
	
	@GetMapping
	public CollectionModel<UsuarioDTO> listar(@PathVariable Long restauranteId){
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		CollectionModel<UsuarioDTO> responsaveisDTO = assemblerUsuario.toCollectionModel(restaurante.getResponsaveis())
																	 .removeLinks()
																	 .add(algaLinks.linkToRestauranteResponsaveis(restauranteId))
																	 .add(algaLinks.linkToRestauranteResponsavelAssociacao(restauranteId));
		responsaveisDTO.getContent().forEach(
				item -> {item.add(algaLinks.linkToRestauranteResponsavelDissociar(restauranteId, item.getId()));}
				);
		
		return responsaveisDTO;
	}
	
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> dissociarRes(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteService.dissociarRes(restauranteId, usuarioId);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associarRes(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteService.associarRes(restauranteId, usuarioId);
		return ResponseEntity.noContent().build();
	}

}
