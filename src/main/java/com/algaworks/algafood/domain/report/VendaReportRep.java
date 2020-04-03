package com.algaworks.algafood.domain.report;

import com.algaworks.algafood.domain.vendaDiariaDomain.VendaDiariaFilter;

public interface VendaReportRep {

	byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
	
}