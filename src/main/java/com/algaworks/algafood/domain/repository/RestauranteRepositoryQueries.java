package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.algaworks.algafood.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {

	List<Restaurante> consultarSDJ(String nome, BigDecimal taxaFrete1, BigDecimal taxaFrete2);
	
	List<Restaurante> findTodos();
	
	List<Restaurante> consultarCriteriaApi(String nome, BigDecimal taxaFrete1, BigDecimal taxaFrete2);

	List<Restaurante> consultarCriteriaApi1(String nome, BigDecimal taxaFrete1, BigDecimal taxaFrete2);

	List<Restaurante> consultarJPQL(String nome);
	
	List<Restaurante> consultarTaxaFreteGratisCriteriaApi(String nome);
	
}