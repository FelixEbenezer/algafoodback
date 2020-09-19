package com.algaworks.algafood.api.v2.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.PedidoDtoAssembler;
import com.algaworks.algafood.api.v1.assembler.PedidoDtoDisassembler;
import com.algaworks.algafood.api.v1.model.PedidoDTO;
import com.algaworks.algafood.api.v1.model.input.PedidoInputDTO2;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.PedidoService;

@RestController
@RequestMapping(value = "/v2/pedidos")
public class PedidoController2 {

	@Autowired
	private AlgaSecurity algaSecurity; 
	
	@Autowired
	private PedidoDtoAssembler assemblerPedido;
	
	@Autowired
	private PedidoDtoDisassembler disassemblerPedido; 
	
	@Autowired
	private PedidoService pedidoService; 
	
	
	@CheckSecurity.Pedidos.PodeCriar
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDTO adicionar (@RequestBody @Valid PedidoInputDTO2 pedidoInputDTO) {
		try {
			Pedido pedido = disassemblerPedido.toDomainObject2(pedidoInputDTO);
			
			pedido.setCliente(new Usuario());
	      //  pedido.getCliente().setId(1L);
			  pedido.getCliente().setId(algaSecurity.getUsuarioId());
		      
	        
	        
			return assemblerPedido.toModel(pedidoService.emitir(pedido));
		}
		catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
}
