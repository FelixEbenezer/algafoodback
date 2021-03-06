package com.algaworks.algafood.domain.listener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;


@Component
public class NotificacaoClientePedidoCanceladoListener {

	@Autowired
	private EnvioEmailService envioEmail; 
	
	@EventListener
	public void aoConfirmarPedido(PedidoCanceladoEvent event) {
		Pedido pedido = event.getPedido();
		
		Set<String> p = new HashSet<>();
		Map<String, Object> ped =  new HashMap<String, Object>();
		Mensagem mensagem = new Mensagem();
				mensagem.setAssunto(pedido.getRestaurante().getNome() + " - Pedido cancelado");
			
				mensagem.setCorpo("pedido-cancelado.html");
				ped.put("pedido", pedido);
				
				mensagem.setVariaveis(ped);
				
				p.add(pedido.getCliente().getEmail());
				mensagem.setDestinatarios(p);
		envioEmail.enviar(mensagem); 
	}
}
