package com.algaworks.algafood.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs;

@Service
public class RestauranteService {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	public CozinhaRepository cozinhaRepository;
	
	// LISTAR ==================================
	
	public List<Restaurante> listarRestaurante() {
		return restauranteRepository.findAll();
	}
	
	// BUSCAR POR ID ==================================
	
	public Optional<Restaurante> bucarPorIdRestaurante (Long id) {
		return restauranteRepository.findById(id);
	}
	
	// ADICIONAR  ==================================
	
	
	public Restaurante adicionarRestaurante (Restaurante restaurante) {
		
		
		Long cozinhaId = restaurante.getCozinha().getId();
		Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId); 
		
		if( cozinha.isEmpty()) {
			throw new EntidadeNaoEncontradaException("Não existe codigo de cozinha fornecido");
		}
		
		restaurante.setCozinha(cozinha.get());
		
		return restauranteRepository.save(restaurante);
		
	}
	
	// REMOVER ==================================
	
	public void removerRestaurante (Long id) {
		try {
		restauranteRepository.deleteById(id);
		}
		
		catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException("O codigo restaurante em uso");
		}
		
		catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException("Não existe o Restaurante");
		}
	}
	
	// CONSULTAR ==================================
	
	public List<Restaurante> consultar1(String nome, Long cozinhaid){
		return restauranteRepository.findByNomeContainingAndCozinhaId(nome, cozinhaid);
	}
	
	public List<Restaurante> consultar2(BigDecimal frete1, BigDecimal frete2){
		return restauranteRepository.findByTaxaFreteBetween(frete1, frete2);
	}
	
	//retornar o primeiro restaurante 
	
	public Optional<Restaurante> consultar3(String nome) {
		return restauranteRepository.findFirstRestauranteByNomeContaining(nome);
	}
	
	// retornar top 2
	
	public List<Restaurante> consultar4(String nome){
		return restauranteRepository.findTop2ByNomeContaining(nome);
	}
	
	// retornar numero de restaurantes numa cozinha atraves de id
	
	public int consultar5(Long id) {
		return restauranteRepository.countByCozinhaId(id);
	}
	
	// retornar um restaurante por nome e 2 valores de taxa frete
	
	public List<Restaurante> consultar6(String nome, BigDecimal frete1, BigDecimal frete2) {
		return restauranteRepository.consultarSDJ(nome, frete1, frete2);
	}
	
	// retornar todos os restaurantes com criteria API
	
	public List<Restaurante> consultar7() {
		return restauranteRepository.findTodos();
	}
	
	// retornar os restaurantes por nome com intervalo de 2 taxafretes sem dinamismo 
	// ou com obrigatoriedade de preencher todos os campos
	
	public List<Restaurante> consultar8(String nome, BigDecimal taxaFrete1, BigDecimal taxaFrete2) {
	 return restauranteRepository.consultarCriteriaApi(nome, taxaFrete1, taxaFrete2);	
	}
	
	// retornar dinamico os restaurantes por nome com intervalo de 2 taxafretes  
		// ou sem obrigatoriedade de preencher todos os campos
		
	public List<Restaurante> consultar9(String nome, BigDecimal taxaFrete1, BigDecimal taxaFrete2) {
		return restauranteRepository.consultarCriteriaApi1(nome, taxaFrete1, taxaFrete2);	
		}
	
	// retorne restaurantes por nome onde taxa frete = 0 usanod @Query
	
	public List<Restaurante> consultar10(String nome) {
		return restauranteRepository.findByNomeContainingAndTaxaFreteZero(nome);
	}
	
	// Retorne restaurantes por nome onde taxaFrete = 0 usando jpql 
	
	public List<Restaurante> consultar11(String nome) {
		return restauranteRepository.consultarJPQL(nome);
	}
	
	// Retorne restaurantes por nome onde taxaFrete = 0 usando criteria API 
	
		public List<Restaurante> consultar12(String nome) {
			return restauranteRepository.consultarTaxaFreteGratisCriteriaApi(nome);
		}
		
	// Retorne restaurantes por nome onde taxaFrete = 0 usando Specifications 
		
	public List<Restaurante> consultar13(String nome) {
		
		// var comFreteGratis = new RestauranteComFreteGratisSpec();
		// var comNomeSemelhante = new RestauranteComNomeSemelhanteSpec(nome);
		
		// return restauranteRepository.findAll(comFreteGratis.and(comNomeSemelhante));
		 return restauranteRepository.findAll(RestauranteSpecs.comFreteGratis().and(RestauranteSpecs.comNomeSemelhante(nome)));
		

	}
	
	// retornar o primeiro registro da entidade restaurante
	public Optional<Restaurante> retornar() {
		return restauranteRepository.buscarPrimeiro();
	}
			
		
}
