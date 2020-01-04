package com.algaworks.algafood.domain.repository;



import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;

@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, 
                 RestauranteRepositoryQueries,
                 JpaSpecificationExecutor<Restaurante>{
	
	@Query("from Restaurante r join fetch r.cozinha join fetch r.formasPagamento join fetch r.endereco.cidade join fetch r.endereco.cidade.estado")
	List<Restaurante> findAll();
	
	List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long Cozinhaid);
	
	List<Restaurante> findByTaxaFreteBetween(BigDecimal frete1, BigDecimal frete2);
	
	
	@Query("from Restaurante where nome like %:nome% and taxaFrete = 0")
	List<Restaurante> findByNomeContainingAndTaxaFreteZero(String nome);
	
	
	Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);
	
	List<Restaurante> findTop2ByNomeContaining(String nome);
	
	int countByCozinhaId(Long id);
	
	//List<Restaurante> findByNomeContainingAndTaxaFreteBetween(String nome, BigDecimal taxaFrete1, BigDecimal taxaFrete2);
	
	//@Query("from Restaurante where nome like %:nome% and taxaFrete between :taxaFrete1 and :taxaFrete2")
	List<Restaurante> consultarNomeTaxaFrete(String nome, BigDecimal taxaFrete1, BigDecimal taxaFrete2);
	
	// List<Restaurante> consultarSDJ(String nome, BigDecimal taxaFrete1, BigDecimal taxaFrete2);
	
}
