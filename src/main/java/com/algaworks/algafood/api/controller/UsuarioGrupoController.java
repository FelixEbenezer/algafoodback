package com.algaworks.algafood.api.controller;

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

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.assembler.GrupoDtoAssembler;
import com.algaworks.algafood.api.model.GrupoDTO;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.UsuarioService;

@RestController
@RequestMapping("/usuarios/{usuarioId}/grupo")
public class UsuarioGrupoController {
	
	@Autowired
	private UsuarioService usuarioService; 
	
	@Autowired
	private GrupoDtoAssembler assembler;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	/*@GetMapping
	public CollectionModel<GrupoDTO> listar (@PathVariable Long usuarioId) {
		Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
		return assembler.toCollectionModel(usuario.getGrupos()).removeLinks();
			            
	}*/
	
//	@Override
	@GetMapping
	public CollectionModel<GrupoDTO> listar(@PathVariable Long usuarioId) {
	    Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
	    
	    CollectionModel<GrupoDTO> gruposModel = assembler.toCollectionModel(usuario.getGrupos())
	            .removeLinks()
	            .add(algaLinks.linkToUsuarioGrupoAssociacao(usuarioId, "associar"));
	    
	    gruposModel.getContent().forEach(grupoModel -> {
	        grupoModel.add(algaLinks.linkToUsuarioGrupoDesassociacao(
	                usuarioId, grupoModel.getId(), "desassociar"));
	    });
	    
	    return gruposModel;
	}    
	
	@PutMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associarGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		usuarioService.associarGrupo(usuarioId, grupoId);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> dissociarGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		usuarioService.dissociarGrupo(usuarioId, grupoId);
		return ResponseEntity.ok().build();
	}

}
