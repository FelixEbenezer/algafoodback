package com.algaworks.algafood.domain.listener;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;


@Component
public class NotificacaoClientePedidoConfirmadoListener {

	@Autowired
	private EnvioEmailService envioEmail; 
	
	@EventListener
	public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
		Pedido pedido = event.getPedido();
		
		Set<String> p = new HashSet<>();
		Map<String, Object> ped =  new HashMap<String, Object>();
		Mensagem mensagem = new Mensagem();
				mensagem.setAssunto(pedido.getRestaurante().getNome() + " - Pedido confirmado");
			
				mensagem.setCorpo("pedido-confirmado.html");
				
				//para listar a data de entrega
				if(pedido.getDataEntrega() != null) {
				ped.put("data_entrega",pedido.getDataEntrega().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
				}
		/*		else {
					String s ="Enviaremos brevemente a data de entrega";
					ped.put("data_entrega",s);
							
				}*/
				   
				ped.put("pedido", pedido);
				
				mensagem.setVariaveis(ped);
				
				p.add(pedido.getCliente().getEmail());
				mensagem.setDestinatarios(p);
		envioEmail.enviar(mensagem); 
	}
}
