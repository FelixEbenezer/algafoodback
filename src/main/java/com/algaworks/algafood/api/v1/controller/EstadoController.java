package com.algaworks.algafood.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.EstadoDtoAssembler;
import com.algaworks.algafood.api.v1.assembler.EstadoDtoDisassembler;
import com.algaworks.algafood.api.v1.assembler.GenericAssembler;
import com.algaworks.algafood.api.v1.model.EstadoDTO;
import com.algaworks.algafood.api.v1.model.input.EstadoInputDTO;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.EstadoService;

@RestController
@RequestMapping("/v1/estados")
public class EstadoController {
	
	@Autowired
	private EstadoDtoAssembler assembler; 
	
	@Autowired
	private GenericAssembler<Estado, EstadoDTO> ass; 
	
	@Autowired
	private EstadoDtoDisassembler disassembler; 
	
	@Autowired
	private EstadoService estadoService;
	
	@CheckSecurity.Estados.PodeConsultar
	@GetMapping
	public List<EstadoDTO> listarEstados() {
		return ass.toCollectionDTO(estadoService.listarEstado(), EstadoDTO.class);
	}
	
	@CheckSecurity.Estados.PodeConsultar
	@GetMapping("/{id}")
	public EstadoDTO buscarPorId(@PathVariable Long id) {
		return ass.toDTO(estadoService.buscarOuFalhar(id), EstadoDTO.class);
		
	}
	
	@CheckSecurity.Estados.PodeEditar
	@PostMapping
	public ResponseEntity<EstadoDTO> adicionar (@RequestBody @Valid EstadoInputDTO estadoInputDTO) {
		Estado es = disassembler.toDomainObject(estadoInputDTO); 
		EstadoDTO esDTO = assembler.toDTO(estadoService.adicionarEstado(es));
		return ResponseEntity.status(HttpStatus.CREATED).body(esDTO);
	}

	//=============ELIMIAR==========================================
	//ANTIGA VERSAO
	/* @DeleteMapping("/{id}")
		public ResponseEntity<?> eliminar (@PathVariable Long id) {
		
		try {
		estadoService.removerEstado(id);
		return ResponseEntity.noContent().build();
		}
		
		catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		
		catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}*/
	
	// VERSAO SIMPLIFICADA
	@CheckSecurity.Estados.PodeEditar
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar (@PathVariable Long id) {
	
	estadoService.removerEstado(id);
}
	
	@CheckSecurity.Estados.PodeEditar
	@PutMapping("/{id}")
	public EstadoDTO atualizar(@PathVariable Long id, @RequestBody @Valid EstadoInputDTO estadoInputDTO) {
		
		
		Estado es = estadoService.buscarOuFalhar(id);
		disassembler.copyToDomainObject(estadoInputDTO, es);
		return assembler.toDTO(estadoService.adicionarEstado(es)); 
		
}
}
