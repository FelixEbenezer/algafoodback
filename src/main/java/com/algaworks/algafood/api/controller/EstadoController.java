package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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


import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	private EstadoService estadoService;
	
	@GetMapping
	public List<Estado> listarEstados() {
		return estadoService.listarEstado();
	}
	
	@GetMapping("/{id}")
	public Estado buscarPorId(@PathVariable Long id) {
		return estadoService.buscarOuFalhar(id);
		
	}
	
	@PostMapping
	public ResponseEntity<Estado> adicionar (@RequestBody @Valid Estado estado) {
		Estado es = estadoService.adicionarEstado(estado);
		return ResponseEntity.status(HttpStatus.CREATED).body(es);
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
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar (@PathVariable Long id) {
	
	estadoService.removerEstado(id);
}
	
	
	@PutMapping("/{id}")
	public Estado atualizar(@PathVariable Long id, @RequestBody @Valid Estado estado) {
		
		
		Estado es = estadoService.buscarOuFalhar(id);
		
		BeanUtils.copyProperties(estado, es, "id");
		estadoService.adicionarEstado(es); 
		
		return es; 
}
}
