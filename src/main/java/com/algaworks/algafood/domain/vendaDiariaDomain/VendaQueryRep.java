package com.algaworks.algafood.domain.vendaDiariaDomain;

import java.util.List;

import com.algaworks.algafood.api.v1.vendaDiariaApi.VendaDiariaDTO;

public interface VendaQueryRep  {
		public List<VendaDiariaDTO> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}
