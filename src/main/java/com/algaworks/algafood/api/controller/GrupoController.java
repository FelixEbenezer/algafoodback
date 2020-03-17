package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.algaworks.algafood.api.assembler.GrupoDtoAssembler;
import com.algaworks.algafood.api.assembler.GrupoDtoDisassembler;
import com.algaworks.algafood.api.model.GrupoDTO;
import com.algaworks.algafood.api.model.input.GrupoInputDTO;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.GrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {
	
	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private GrupoDtoAssembler assembler; 
	
	@Autowired
	private GrupoDtoDisassembler disassembler; 
	
	@GetMapping
	public List<GrupoDTO> listar() {
		return assembler.toCollectionObject(grupoService.listarGrupo());
	}
	
	@GetMapping("/{id}")
	public GrupoDTO buscarPorId (@PathVariable Long id) {
		return assembler.toDTO(grupoService.buscarOuFalhar(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoDTO adicionar (@RequestBody @Valid GrupoInputDTO grupoInputDTO) {
		Grupo grupo = disassembler.toDomainObject(grupoInputDTO);
		GrupoDTO grupoDTO = assembler.toDTO(grupoService.adicionarGrupo(grupo));
		
		return grupoDTO;
	}
	
	
	@PutMapping("/{id}")
	public GrupoDTO atualizar (@PathVariable Long id, @RequestBody @Valid GrupoInputDTO grupoInputDTO) {
		Grupo grupo = grupoService.buscarOuFalhar(id);
		disassembler.copyToDomainObject(grupoInputDTO, grupo);
		return assembler.toDTO(grupoService.adicionarGrupo(grupo));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover (@PathVariable Long id) {
		grupoService.remover(id);
	}

}