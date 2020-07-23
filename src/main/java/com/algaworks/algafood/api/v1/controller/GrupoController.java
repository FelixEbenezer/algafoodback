package com.algaworks.algafood.api.v1.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.GrupoDtoAssembler;
import com.algaworks.algafood.api.v1.assembler.GrupoDtoDisassembler;
import com.algaworks.algafood.api.v1.model.GrupoDTO;
import com.algaworks.algafood.api.v1.model.input.GrupoInputDTO;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.GrupoService;

@RestController
@RequestMapping("/v1/grupos")
public class GrupoController {
	
	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private GrupoDtoAssembler assembler; 
	
	@Autowired
	private GrupoDtoDisassembler disassembler; 
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@GetMapping
	public CollectionModel<GrupoDTO> listar() {
		return assembler.toCollectionModel(grupoService.listarGrupo());
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@GetMapping("/{id}")
	public GrupoDTO buscarPorId (@PathVariable Long id) {
		return assembler.toModel(grupoService.buscarOuFalhar(id));
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoDTO adicionar (@RequestBody @Valid GrupoInputDTO grupoInputDTO) {
		Grupo grupo = disassembler.toDomainObject(grupoInputDTO);
		GrupoDTO grupoDTO = assembler.toModel(grupoService.adicionarGrupo(grupo));
		
		return grupoDTO;
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@PutMapping("/{id}")
	public GrupoDTO atualizar (@PathVariable Long id, @RequestBody @Valid GrupoInputDTO grupoInputDTO) {
		Grupo grupo = grupoService.buscarOuFalhar(id);
		disassembler.copyToDomainObject(grupoInputDTO, grupo);
		return assembler.toModel(grupoService.adicionarGrupo(grupo));
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover (@PathVariable Long id) {
		grupoService.remover(id);
	}

}
