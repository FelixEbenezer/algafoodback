package com.algaworks.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.GetStatusPedidoDtoAssembler;
import com.algaworks.algafood.api.model.GetStatusPedidoDTO;
import com.algaworks.algafood.domain.service.StatusPedidoService;

@RestController
@RequestMapping(value = "/pedidos/{codigo}")
public class StatusPedidoController {

	@Autowired
	private StatusPedidoService statusPedidoService; 
	
	@Autowired
	private GetStatusPedidoDtoAssembler assemblerGetStatus; 
	
	@PutMapping("/confirmar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void confirmar(@PathVariable String codigo) {
		
		statusPedidoService.confirmar(codigo);
		
	}
	
	@PutMapping("/entregar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void entregar(@PathVariable String codigo) {
		
		statusPedidoService.entregar(codigo);
		
	}
	
	@PutMapping("/cancelar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancelar(@PathVariable String codigo) {
		
		statusPedidoService.cancelar(codigo);
		
	}
	
	@GetMapping("/status")
	public GetStatusPedidoDTO buscarPorId (@PathVariable String codigo) {
		return assemblerGetStatus.toDTO(statusPedidoService.buscarPorId(codigo));
	}
}
