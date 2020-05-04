package com.algaworks.algafood.domain.listener;


import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class DataEntregaMaisTresPedidoConfirmadoListener {

	
	@EventListener
	public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
		Pedido pedido = event.getPedido();
		try {
		if (pedido.getDataEntrega()==null && pedido.getDataConfirmacao() !=null){
		    pedido.setDataEntrega(pedido.getDataCriacao().plusDays(3));
		    }
		} catch (Exception e) {
			throw new NegocioException("Não é possivel confirmar este pedido %s"+pedido.getStatus());
		}
		
	}
}
