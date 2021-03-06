package com.algaworks.algafood.domain.report;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.vendaDiariaDomain.VendaDiariaFilter;
import com.algaworks.algafood.domain.vendaDiariaDomain.VendaQueryRep;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class PdfVendaReportRepImpl implements VendaReportRep {

	@Autowired
	private VendaQueryRep vendaQueryRep;
	
	@Override
	public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
		try {
			var inputStream = this.getClass().getResourceAsStream(
					"/relatorios/Vendas.jasper");
			   
			var vendasDiarias = vendaQueryRep.consultarVendasDiarias(filtro, timeOffset);
			var dataSource = new JRBeanCollectionDataSource(vendasDiarias);
			
			var parametros = new HashMap<String, Object>();
			parametros.put("REPORT_LOCALE", new Locale("fr", "FR"));
			    
			//para adicionar totatais gerias e numero de dias no relatorio
			parametros.put("TOTAL_GERAL", vendasDiarias.stream()
					.map(item -> item.getTotalFaturado())
					.reduce(BigDecimal.ZERO, BigDecimal::add).doubleValue());
			parametros.put("TOTAL_DIAS", vendasDiarias.size());
			
			var jasperPrint = JasperFillManager.fillReport(inputStream, parametros, dataSource);
		
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (Exception e) {
			throw new ReportException("Não foi possível emitir relatório de vendas diárias", e);
		}
	}

}