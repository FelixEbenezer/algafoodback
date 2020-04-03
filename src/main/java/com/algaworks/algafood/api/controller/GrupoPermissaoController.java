package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.PermissaoDtoAssembler;
import com.algaworks.algafood.api.model.PermissaoDTO;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.GrupoService;

@RestController
@RequestMapping(value = "/grupos/{grupoId}/permissao")
public class GrupoPermissaoController {
	
	@Autowired
	private GrupoService grupoService; 
	
	@Autowired
	private PermissaoDtoAssembler assembler; 
		
	@GetMapping
	public List<PermissaoDTO> listarPermissaoGrupo(@PathVariable Long grupoId) {
		Grupo grupo = grupoService.buscarOuFalhar(grupoId);
		return assembler.toCollectionObject(grupo.getPermissoes()); 
	}
	
	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		grupoService.associarPermissao(grupoId, permissaoId);
	}
	
	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void dissociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		grupoService.dissociarPermissao(grupoId, permissaoId);
	}
	
	

}
