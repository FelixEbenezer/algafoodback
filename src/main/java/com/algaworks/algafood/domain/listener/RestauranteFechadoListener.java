package com.algaworks.algafood.domain.listener;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.event.RestauranteFechadoEvent;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;
import com.algaworks.algafood.domain.vendaDiariaDomain.VendaDiariaFilter;
import com.algaworks.algafood.domain.vendaDiariaDomain.VendaQueryRep;


@Component
public class RestauranteFechadoListener {

	@Autowired
	private EnvioEmailService envioEmail; 
	
	//@Autowired
	// private VendaReportRep vendadiario;
	
	@Autowired
	private VendaQueryRep vendaQuery;
	
	@EventListener
	public void aoConfirmarPedido(RestauranteFechadoEvent event) {
		Restaurante restaurante = event.getRestaurante();
		
		Set<String> p = new HashSet<>();
		Map<String, Object> ped =  new HashMap<String, Object>();
		Mensagem mensagem = new Mensagem();
		mensagem.setAssunto(restaurante.getNome() + " - Relatório diário");
	
		mensagem.setCorpo("restaurante-fechado.html");
		
		SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
		String hoje = formatador.format(new Date());
		
		VendaDiariaFilter filtro =  new VendaDiariaFilter();
	//	filtro.setDataCriacaoInicio(OffsetDateTime.parse("2020-05-14T09:30:00.690-05:00"));
	//	filtro.setDataCriacaoFim(OffsetDateTime.parse("2020-05-14T23:30:30.690-05:00"));
		
	//	filtro.setDataCriacaoInicio(OffsetDateTime.parse("2020-03-19T16:16:35.690-05:00"));
	//	filtro.setDataCriacaoFim(OffsetDateTime.parse("2020-03-24T16:16:35.690-05:00"));
		
		filtro.setDataCriacaoInicio(OffsetDateTime.parse(hoje+
				"T00:00:00.000-05:00"));
		filtro.setDataCriacaoFim(OffsetDateTime.parse(hoje+
				"T23:59:59.000-05:00"));
		
		filtro.setRestauranteId(restaurante.getId());
		
		var vendasDiarias = vendaQuery.consultarVendasDiarias(filtro , "+00:00");
		
/*		List<Restaurante> res = Arrays.asList();
		res.forEach(item -> {
			ped.put("TOTAL_GERAL", vendasDiarias.stream()
					.map(item1 -> item1.getTotalFaturado())
					.reduce(BigDecimal.ZERO, BigDecimal::add).doubleValue());
			ped.put("TOTAL_DIAS", vendasDiarias.size());
			
			ped.put("restaurante", restaurante);}
			);
				
	*/		
		
		ped.put("TOTAL_GERAL", vendasDiarias.stream()
				.map(item -> item.getTotalFaturado())
				.reduce(BigDecimal.ZERO, BigDecimal::add).doubleValue());
		ped.put("TOTAL_DIAS", vendasDiarias.size());
		
		ped.put("restaurante", restaurante);
		
		
		
		mensagem.setVariaveis(ped);
		
		p.add(restaurante.getResponsaveis().stream()
				.map(u -> u.getEmail()).toString());
		mensagem.setDestinatarios(p);
		envioEmail.enviar(mensagem);
	}
}
