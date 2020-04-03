package com.algaworks.algafood.domain.vendaDiariaDomain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.api.vendaDiariaApi.VendaDiariaDTO;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;

@Repository
public class VendaQueryRepImpl implements VendaQueryRep{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<VendaDiariaDTO> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
		
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(VendaDiariaDTO.class);
		var root = query.from(Pedido.class);
		
		//filtro
		var predicates = new ArrayList<Predicate>();
		
		// converteT_z
		var functionConvertTzDataCriacao = builder.function(
				"convert_tz", Date.class, root.get("dataCriacao"),
				builder.literal("+00:00"), builder.literal(timeOffset));
		
		var functionDateDataCriacao = builder.function(
				"date", Date.class, functionConvertTzDataCriacao);
		 
		var selection = builder.construct(VendaDiariaDTO.class,
				functionDateDataCriacao,
				//builder.equal(root.get("restaurante"), filtro.getRestauranteId()),
				builder.count(root.get("id")),
				builder.sum(root.get("valorTotal")));
		
		// filtrando comparando
		if (filtro.getRestauranteId() != null) {
			predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));
		}
			      
		if (filtro.getDataCriacaoInicio() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), 
					filtro.getDataCriacaoInicio()));
		}

		if (filtro.getDataCriacaoFim() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), 
					filtro.getDataCriacaoFim()));
		}
		
		predicates.add(root.get("status").in(
				StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));
		
		query.select(selection);
		query.where(predicates.toArray(new Predicate[0]));
		query.groupBy(functionDateDataCriacao);
		
		return manager.createQuery(query).getResultList();
		
	}

}
