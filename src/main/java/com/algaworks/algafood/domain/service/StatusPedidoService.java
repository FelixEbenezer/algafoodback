package com.algaworks.algafood.domain.service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;
import com.algaworks.algafood.infrastructure.repository.email.SmtpEnvioEmailService;

@Service
public class StatusPedidoService {

	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private EnvioEmailService envioEmail;
	
	//metodo simplificado
	@Transactional
	public void confirmar(String codigo) {
		Pedido pedido = pedidoService.buscarOufalhar(codigo); 
		pedido.confirmar();
		
		Set<Pedido> pedidos = new  HashSet<Pedido>();
		
		Mensagem mensagem = new Mensagem();
				mensagem.setAssunto(pedido.getRestaurante().getNome() + " - Pedido confirmado");
				mensagem.setCorpo("O pedido de código <strong>" 
						+ pedido.getCodigo() + "</strong> foi confirmado!");
				mensagem.setDestinatarios(pedidos.stream()
										.map(item -> item.getCliente().getEmail())
										 .collect(Collectors.toSet()));
		
		envioEmail.enviar(mensagem);
	}
	
	//antes da simplificacao
	/*
	@Transactional
	public void confirmar(Long pedidoId) {
		Pedido pedido = pedidoService.buscarOufalhar(pedidoId); 
		
		if(!pedido.getStatus().equals(StatusPedido.CRIADO)) {
			throw new NegocioException(String.format(
					"o pedido de codigo %d inserido de estatus %s não pode"
					+ "ser ser confirmado", pedido.getId(), 
					pedido.getStatus()));
		}
		
		pedido.setStatus(StatusPedido.CONFIRMADO);
		pedido.setDataConfirmacao(OffsetDateTime.now());
	}
	*/

	//metodo simplificado
	@Transactional
	public void entregar(String codigo) {
		Pedido pedido = pedidoService.buscarOufalhar(codigo); 
		pedido.entregar();
	}

	//antes da simplificacao
	/*
	@Transactional
	public void entregar(Long pedidoId) {
		Pedido pedido = pedidoService.buscarOufalhar(pedidoId); 
		
		if(pedido.getStatus().equals(StatusPedido.CONFIRMADO)) {
			pedido.setStatus(StatusPedido.ENTREGUE);
			pedido.setDataEntrega(OffsetDateTime.now());
		}
		else {
			throw new NegocioException(String.format(
					"o pedido de codigo %d inserido de estatus %s não pode"
					+ "ser entregue falta de confirmacao", pedido.getId(), 
					pedido.getStatus()));
		}
		
	}
	*/
	
	//metodo simplificado
	

	@Transactional
	public void cancelar(String codigo) {
		Pedido pedido = pedidoService.buscarOufalhar(codigo); 
		pedido.cancelar();
	}

	
	//antes da simplificacao
	/*
	@Transactional
	public void cancelar(Long pedidoId) {
		Pedido pedido = pedidoService.buscarOufalhar(pedidoId); 
		
		if( (pedido.getStatus().equals(StatusPedido.ENTREGUE))) {
			throw new NegocioException(String.format(
					"o pedido de codigo %d inserido de estatus %s não pode"
					+ "ser cancelado", pedido.getId(), 
					pedido.getStatus()));
		}
		
		pedido.setStatus(StatusPedido.CANCELADO);
		pedido.setDataCancelamento(OffsetDateTime.now());
	}
	
*/	
	@Transactional
	public Pedido buscarPorId (String codigo) {
		return pedidoService.buscarOufalhar(codigo); 
		
	}
	
	
}
