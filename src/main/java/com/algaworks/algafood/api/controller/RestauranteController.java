package com.algaworks.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	public RestauranteService restauranteService;
	
	@GetMapping
	public List<Restaurante> listar () {
		return restauranteService.listarRestaurante();
	}
	
	
	
	@PostMapping
	public ResponseEntity<?> salvar (@RequestBody Restaurante restaurante) {
		try {
		Restaurante restaurante1 = restauranteService.adicionarRestaurante(restaurante);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(restaurante1);
		}
		
		catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Restaurante> buscarPorId (@PathVariable Long id) {
		
		Optional<Restaurante> restaurante = restauranteService.bucarPorIdRestaurante(id);
		
		if ( restaurante.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(restaurante.get());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Restaurante> remover (@PathVariable Long id) {
		try {
		
		restauranteService.removerRestaurante(id);
		return ResponseEntity.noContent().build();
		}
		
		catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
		catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	
	
	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> atualizar(@PathVariable Long restauranteId,
			@RequestBody Restaurante restaurante) {
		try {
			Optional<Restaurante> restauranteAtual = restauranteService.bucarPorIdRestaurante(restauranteId);
			
			if (restauranteAtual.isPresent()) {
				BeanUtils.copyProperties(restaurante, restauranteAtual.get(), 
						"id", "formasPagamento", "endereco", "dataCadastro", "produtos");
				
				restauranteService.adicionarRestaurante(restauranteAtual.get());
				return ResponseEntity.ok(restauranteAtual.get());
			}
			
			return ResponseEntity.notFound().build();
		
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest()
					.body(e.getMessage());
		}
	}
	
	//CONSULTAS ======================================================
	
	@GetMapping("/por-nome-id")
	public List<Restaurante> consultarPrimeiro(String nome, Long cozinhaid) {
		return restauranteService.consultar1(nome, cozinhaid);
	}
	
	@GetMapping("/por-taxa")
	public List<Restaurante> consultarSegundo(BigDecimal frete1, BigDecimal frete2) {
		return restauranteService.consultar2(frete1, frete2);
	}
	
	// retornar o primeiro restaurante
	@GetMapping("/por-primeiro-restaurante")
	public Optional<Restaurante> consultarPrimeiroRes(String nome) {
		return restauranteService.consultar3(nome);
	}
	
	// retornar top 2
	@GetMapping("/por-top2")
	public List<Restaurante> consultarTop2(String nome) {
		return restauranteService.consultar4(nome);
	}
	
	// retornar numero de restaurantes numa cozinha atraves de id
	@GetMapping("/por-cozinhaid")
	public int consultarPorCozinhaId(Long cozinhaid) {
		return restauranteService.consultar5(cozinhaid);
	}
	
	//retornar uma lista de restaurante por  nome e entre 2 valores de frete
	@GetMapping("/por-nome-taxaFrete")
	public List<Restaurante> consultarPorNomeEtaxaFrete(String nome, BigDecimal frete1, BigDecimal frete2) {
		return restauranteService.consultar6(nome, frete1, frete2);
	}
	
	// retornar todos os restaurantes usando criteria API
	
	@GetMapping("/por-todos")
	public List<Restaurante> consulatTodos() {
		return restauranteService.consultar7();
	}
	
	// retornar os restaurantes por nome com intervalo de 2 taxafretes sem dinamismo 
		// ou com obrigatoriedade de preencher todos os campos
	
	@GetMapping("/por-nome-intervalo-taxa")
	public List<Restaurante> consultarNomeCriteria(String nome, BigDecimal taxaFrete1, BigDecimal taxaFrete2) {
	return restauranteService.consultar8(nome, taxaFrete1, taxaFrete2);	
		}
	
	
	// retorno dinamico dos restaurantes por nome com intervalo de 2 taxafretes 
	// sem obrigatoriedade de preencher todos os campos
		
	@GetMapping("/por-nome-intervalo-taxa-2")
	public List<Restaurante> consultarNomeCriteria1(String nome, BigDecimal taxaFrete1, BigDecimal taxaFrete2) {
	return restauranteService.consultar9(nome, taxaFrete1, taxaFrete2);	
		}
	
	//retorne restaurantes por nome com taxaFrete = 0
	@GetMapping("/por-nome-taxa-gratis")
	public List<Restaurante> consultarNomeTaxa(String nome) {
		return restauranteService.consultar10(nome);
	}
	
	// Retorne restaurantes por nome onde taxaFrete = 0 usando jpql 
	@GetMapping("/por-nome-taxa-gratis-jpql")
	public List<Restaurante> consultarNomeTaxa1(String nome) {
		return restauranteService.consultar11(nome);
	}
	
	// Retorne restaurantes por nome onde taxaFrete = 0 usando criteria API 
		@GetMapping("/por-nome-taxa-gratis-criteria")
		public List<Restaurante> consultarNomeTaxa2(String nome) {
			return restauranteService.consultar12(nome);
		}
	
	// Retorne restaurantes por nome onde taxaFrete = 0 usando Specifications
	@GetMapping("/por-nome-taxa-gratis-spec")
	public List<Restaurante> consultarNomeTaxa3(String nome) {
		return restauranteService.consultar13(nome);
	}
	
	// retorne o primeiro registro cadastrado na entidade restaurante
	@GetMapping("/por-primeiro")
	public Optional<Restaurante> retornarPrimeiro() {
		return restauranteService.retornar();
	}




}
