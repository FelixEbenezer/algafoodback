package com.algaworks.algafood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Restaurante> consultarSDJ(String nome, BigDecimal taxaFrete1, BigDecimal taxaFrete2) {
		
		var jpql = new StringBuilder();
		jpql.append("from Restaurante where 0 = 0 ");
		
		var parametros = new HashMap<String, Object>();
		
		if (StringUtils.hasLength(nome)) {
			jpql.append("and nome like :nome ");
			parametros.put("nome", "%" + nome + "%");
		}
		
		if (taxaFrete1 != null) {
			jpql.append("and taxaFrete >= :taxaFrete1 ");
			parametros.put("taxaFrete1", taxaFrete1);
		}
		
		if (taxaFrete2 != null) {
			jpql.append("and taxaFrete <= :taxaFrete2 ");
			parametros.put("taxaFrete2", taxaFrete2);
		}
		
		TypedQuery<Restaurante> query = manager
				.createQuery(jpql.toString(), Restaurante.class);
		
		parametros.forEach((chave, valor) -> query.setParameter(chave, valor));

		return query.getResultList();
		
	}
	
	// COnsulta simples usando criteria API
	
	public List<Restaurante> findTodos() {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
		criteria.from(Restaurante.class);
		
		TypedQuery<Restaurante> query = manager.createQuery(criteria);
		
		return query.getResultList();
	}
	
	// consulta que retorna nome por intervalo de 2 taxafretes sem ser dinamico
	
	public List<Restaurante> consultarCriteriaApi(String nome, BigDecimal taxaFrete1, BigDecimal taxaFrete2) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
		Root<Restaurante> root = criteria.from(Restaurante.class);
		
		Predicate nomePredicate = builder.like(root.get("nome"), "%" + nome +"%");
		
		Predicate taxaFrete1Predicate = builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFrete1);
		
		Predicate taxaFrete2Predicate = builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFrete2);
		
		criteria.where(nomePredicate, taxaFrete1Predicate, taxaFrete2Predicate); 
		
		TypedQuery<Restaurante> query = manager.createQuery(criteria);
		
		return query.getResultList();
	}
	
	// consulta dinamica que retorna nome por intervalo de 2 taxafretes
	
		public List<Restaurante> consultarCriteriaApi1(String nome, BigDecimal taxaFrete1, BigDecimal taxaFrete2) {
			var builder = manager.getCriteriaBuilder();
			
			var criteria = builder.createQuery(Restaurante.class);
			var root = criteria.from(Restaurante.class);
			
			var predicates = new ArrayList<Predicate>();
			
			if(StringUtils.hasText(nome)) {
			predicates.add(builder.like(root.get("nome"), "%" + nome +"%"));
			}
			
			if(taxaFrete1 != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFrete1));
			}
			
			if(taxaFrete2 != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFrete2));
				}
			
			criteria.where(predicates.toArray(new Predicate[0])); 
			
			TypedQuery<Restaurante> query = manager.createQuery(criteria);
			
			return query.getResultList();
		}

		// Retorne nome com taxaFrete = 0 usando jpql 
		@Override
		public List<Restaurante> consultarJPQL(String nome) {
			var jpql = new StringBuilder();
			jpql.append("from Restaurante where taxaFrete = 0 ");
			
			var parametros = new HashMap<String, Object>();
			
			if (StringUtils.hasLength(nome)) {
				jpql.append("and nome like :nome ");
				parametros.put("nome", "%" + nome + "%");
			}
			
			TypedQuery<Restaurante> query = manager
					.createQuery(jpql.toString(), Restaurante.class);
			
			parametros.forEach((chave, valor) -> query.setParameter(chave, valor));

			return query.getResultList();

		}

		// Retorne nome com taxaFrete = 0 usando Criteria API
		@Override
		public List<Restaurante> consultarTaxaFreteGratisCriteriaApi(String nome) {
            var builder = manager.getCriteriaBuilder();
			
			var criteria = builder.createQuery(Restaurante.class);
			var root = criteria.from(Restaurante.class);
			
			var predicates = new ArrayList<Predicate>();
			
			if((StringUtils.hasText(nome))) {
			predicates.add(builder.like(root.get("nome"), "%" + nome +"%"));
			predicates.add(builder.equal(root.get("taxaFrete"), 0 )) ;
			}
			criteria.where(predicates.toArray(new Predicate[0])); 
			
			TypedQuery<Restaurante> query = manager.createQuery(criteria);
			
			return query.getResultList();

		}

}
