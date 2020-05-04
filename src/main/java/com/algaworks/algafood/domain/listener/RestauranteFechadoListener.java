package com.algaworks.algafood.domain.listener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.event.RestauranteFechadoEvent;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.report.VendaReportRep;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;


@Component
public class RestauranteFechadoListener {

	@Autowired
	private EnvioEmailService envioEmail; 
	
	@Autowired
	private VendaReportRep vendadiario;
	
	@EventListener
	public void aoConfirmarPedido(RestauranteFechadoEvent event) {
		Restaurante restaurante = event.getRestaurante();
		
		Set<String> p = new HashSet<>();
		Map<String, Object> ped =  new HashMap<String, Object>();
		Mensagem mensagem = new Mensagem();
				mensagem.setAssunto(restaurante.getNome() + " - Relatório diário");
			
				mensagem.setCorpo("relatorio-diario.pdf");
			//	vendadiario.emitirVendasDiarias(filtro, timeOffset);
				
				ped.put("restaurante", restaurante);
				
				mensagem.setVariaveis(ped);
				
				p.add(restaurante.getResponsaveis().stream()
						.map(u -> u.getEmail()).toString());
				mensagem.setDestinatarios(p);
		envioEmail.enviar(mensagem); 
	}
}
