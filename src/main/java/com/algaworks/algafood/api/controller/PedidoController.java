package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.PedidoDtoAssembler;
import com.algaworks.algafood.api.assembler.PedidoDtoDisassembler;
import com.algaworks.algafood.api.assembler.PedidoResumoDtoAssembler;
import com.algaworks.algafood.api.model.PedidoDTO;
import com.algaworks.algafood.api.model.PedidoResumoDTO;
import com.algaworks.algafood.api.model.input.PedidoInputDTO;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

	@Autowired
	private PedidoDtoAssembler assemblerPedido;
	
	@Autowired
	private PedidoResumoDtoAssembler assemblerPedidoResumo; 
	
	@Autowired
	private PedidoDtoDisassembler disassemblerPedido; 
	
	@Autowired
	private PedidoService pedidoService; 
	
	@GetMapping
	public List<PedidoResumoDTO> consular() {
		return assemblerPedidoResumo.toCollectionObject(pedidoService.listar());
	}
	
	@GetMapping("/{pedidoId}")
	public PedidoDTO buscarPor (@PathVariable Long pedidoId) {
		return assemblerPedido.toDTO(pedidoService.buscarPorId(pedidoId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDTO adicionar (@RequestBody @Valid PedidoInputDTO pedidoInputDTO) {
		try {
			Pedido pedido = disassemblerPedido.toDomainObject(pedidoInputDTO);
			
			pedido.setCliente(new Usuario());
	        pedido.getCliente().setId(1L);
	        
	        
	        
			return assemblerPedido.toDTO(pedidoService.emitir(pedido));
		}
		catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
}
