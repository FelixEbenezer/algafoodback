package com.algaworks.algafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.GetStatusPedidoDtoAssembler;
import com.algaworks.algafood.api.v1.model.GetStatusPedidoDTO;
import com.algaworks.algafood.domain.service.StatusPedidoService;

@RestController
@RequestMapping(value = "/v1/pedidos/{codigo}")
public class StatusPedidoController {

	@Autowired
	private StatusPedidoService statusPedidoService; 
	
	@Autowired
	private GetStatusPedidoDtoAssembler assemblerGetStatus; 
	
	@PutMapping("/confirmar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> confirmar(@PathVariable String codigo) {
		
		statusPedidoService.confirmar(codigo);
		return ResponseEntity.noContent().build();
		
	}
	
	@PutMapping("/entregar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> entregar(@PathVariable String codigo) {
		
		statusPedidoService.entregar(codigo);
		return ResponseEntity.noContent().build();

	}
	
	@PutMapping("/cancelar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> cancelar(@PathVariable String codigo) {
		
		statusPedidoService.cancelar(codigo);
		return ResponseEntity.noContent().build();

	}
	
	@GetMapping("/status")
	public GetStatusPedidoDTO buscarPorId (@PathVariable String codigo) {
		//implementar adicionar links aqui sel e Rel
		GetStatusPedidoDTO statusdto= assemblerGetStatus.toDTO(statusPedidoService.buscarPorId(codigo));
		statusdto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StatusPedidoController.class).buscarPorId(codigo)).withSelfRel());
		
		return statusdto; 
	}
}
