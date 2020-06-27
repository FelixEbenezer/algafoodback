package com.algaworks.algafood.api.v1.vendaDiariaApi;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.report.VendaReportRep;
import com.algaworks.algafood.domain.vendaDiariaDomain.VendaDiariaFilter;
import com.algaworks.algafood.domain.vendaDiariaDomain.VendaQueryRep;

@RestController
@RequestMapping(path = "/estatisticas")
public class EstatisticasController {
	
	@Autowired
	private VendaQueryRep vendaQueryRep;
	
	
	@Autowired
	private VendaReportRep vendaReportRep; 
	
	/*	@Autowired
	private AlgaLinks algaLinks;
	
	public static class VendaDiariaDTO extends RepresentationModel<VendaDiariaDTO> {
	}

	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public VendaDiariaDTO estatisticas() {
	    var estatisticasModel = new VendaDiariaDTO();
	    
	    estatisticasModel.add(algaLinks.linkToEstatisticasVendasDiarias("vendas-diarias"));
	    
	    return estatisticasModel;
	}*/  
	
	
	/*
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendaDiariaDTO> consultarVendas (VendaDiariaFilter filtro, 
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
		
		return vendaQueryRep.consultarVendasDiarias(filtro, timeOffset); 
	}
	*/
	@GetMapping(path = "/vendas-diarias/total-geral", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> consultarVendasTotalGeralEmUri (VendaDiariaFilter filtro, 
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
		
		List<VendaDiariaDTO> vendas=  vendaQueryRep.consultarVendasDiarias(filtro, timeOffset);
		
		Map<String, Object> json = new LinkedHashMap<>();
		
		json.put("content", vendas);
		json.put("Nº de Dias de Vendas", vendas.size());
		//json.put("numberOfElements", vendas.getTotalElements());
		json.put("Total Geral", vendas.stream()
									 .map(item-> item.getTotalFaturado()).reduce(BigDecimal.ZERO,BigDecimal::add));
		
		return json;
	}
	
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> consultarVendasTotalGeralComoParametro (VendaDiariaFilter filtro, 
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset, @RequestParam(required = false) String total) {
		
		List<VendaDiariaDTO> vendas=  vendaQueryRep.consultarVendasDiarias(filtro, timeOffset);
		
		
		
		Map<String, Object> json = new LinkedHashMap<>();
		
		if("total-geral".equals(total)) {
		 json.put("content", vendas);
		 json.put("Nº de Dias de Vendas", vendas.size());
		 json.put("Total Geral", vendas.stream()
										 .map(item-> item.getTotalFaturado()).reduce(BigDecimal.ZERO,BigDecimal::add));
           }
		
		json.put("content", vendas);
		
		
		return json;
	}
	
	
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
		
		byte[] bytesPdf = vendaReportRep.emitirVendasDiarias(filtro, timeOffset);
		
		var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");
		
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(bytesPdf);
	}
	
	

}
