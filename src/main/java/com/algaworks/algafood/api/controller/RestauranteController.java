package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.RestauranteDtoAssembler;
import com.algaworks.algafood.api.assembler.RestauranteDtoDisassembler;
import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.api.model.input.RestauranteInputDTO;
import com.algaworks.algafood.api.model.view.RestauranteView;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	private RestauranteDtoAssembler assembler; 
	
	@Autowired
	private RestauranteDtoDisassembler disassembler; 
	
	@Autowired
	public RestauranteService restauranteService;
	
	
/*	@GetMapping
	public List<RestauranteDTO> listar () {
		return assembler.toCollectionDTO(restauranteService.listarRestaurante());
	}
	
	@JsonView(RestauranteView.Resumo.class)
	@GetMapping(params = "projecao=resumo")
	public List<RestauranteDTO> listarResumo () {
		return assembler.toCollectionDTO(restauranteService.listarRestaurante());
	}
	
	@JsonView(RestauranteView.ApenasNome.class)
	@GetMapping(params = "projecao=Apenas-nome")
	public List<RestauranteDTO> listarApenasNome () {
		return assembler.toCollectionDTO(restauranteService.listarRestaurante());
	}*/
	
	@ApiImplicitParams({
		@ApiImplicitParam(value="Nome da projecao de restaurantes", allowableValues = "apenas-nome, completo",
		name= "projecao", paramType = "query", type="string")
	})
	@GetMapping
	public MappingJacksonValue listar (@RequestParam (required = false) String projecao) {
		
		List<Restaurante> restaurantes = restauranteService.listarRestaurante();
		List<RestauranteDTO> restaurantesDTO = assembler.toCollectionDTO(restaurantes);
		
		MappingJacksonValue restauranteWrapper = new MappingJacksonValue(restaurantesDTO);
		
		restauranteWrapper.setSerializationView(RestauranteView.Resumo.class);
		
		if("apenas-nome".equals(projecao))
		{
			restauranteWrapper.setSerializationView(RestauranteView.ApenasNome.class);
				
		}
		else if("completo".equals(projecao)) {
			restauranteWrapper.setSerializationView(null);
			
		}
		
		return restauranteWrapper; 
	}
	
	
		
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO salvar (@RequestBody @Valid RestauranteInputDTO restauranteInputDTO) {
		try {
			
			Restaurante restaurante = disassembler.toDomainObject(restauranteInputDTO);
		return assembler.toDTO(restauranteService.adicionarRestaurante(restaurante));
		
		// return ResponseEntity.status(HttpStatus.CREATED).body(restaurante1);
		}
		
		catch (CozinhaEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
			//return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
		
	}
	
	//===============ABERTURA/FECHO DO RESTAURANTE=====================
	
	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void abrir(@PathVariable Long restauranteId) {
		restauranteService.abrir(restauranteId);
	}
	
	@PutMapping("/{restauranteId}/fecho")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long restauranteId) {
		restauranteService.fechar(restauranteId);
	}
	
	
//==========BUSCAR POR ID===================================================
// ANTIGA VERSAO ===================
	
/* @GetMapping("/{id}")
	public ResponseEntity<Restaurante> buscarPorId (@PathVariable Long id) {
		
		Optional<Restaurante> restaurante = restauranteService.bucarPorIdRestaurante(id);
		
		if ( restaurante.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(restaurante.get());
	}*/

// VERSAO SIMPLIFCADA 
/*	@GetMapping("/{id}")
	public Restaurante buscarPorId (@PathVariable Long id) {
		
		return restauranteService.buscarOuFalhar(id);
	}*/
//==============================================================================

	// VERSAO SIMPLIFCADA DTO
	// @JsonView(RestauranteView.Resumo.class)
		@GetMapping("/{id}")
		public RestauranteDTO buscarPorId (@PathVariable Long id) {
			
			Restaurante restaurante = restauranteService.buscarOuFalhar(id);
			
			return assembler.toDTO(restaurante);

		}



	//==============================================================================
	
//==============REMOVER===============================================
	//ANTIGA VERSAO 
	
/*	@DeleteMapping("/{id}")
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
	}*/
	
	// VERSAO SIMPLIFICADA
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover (@PathVariable Long id) {
	
		restauranteService.removerRestaurante(id);
		
	}
//=============================================================================
	
	
//=========ATUALIZAR============================================================
	//ANTIGA VERSAO
	
/*	@PutMapping("/{restauranteId}")
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
	} */

	// VERSAO SIMPLIFICADA
	
	@PutMapping("/{restauranteId}")
	public RestauranteDTO atualizar(@PathVariable Long restauranteId,
			@RequestBody @Valid RestauranteInputDTO restauranteInputDTO) {
		    
		// Restaurante restaurante = disassembler.toDomainObject(restauranteInputDTO);
		     Restaurante restauranteAtual = restauranteService.buscarOuFalhar(restauranteId);
			
		     disassembler.copyToDomainObject(restauranteInputDTO, restauranteAtual);
		     // BeanUtils.copyProperties(restaurante, restauranteAtual, 
				//		"id", "formasPagamento", "endereco", "dataCadastro", "produtos");
				
			 try {
				 return assembler.toDTO(restauranteService.adicionarRestaurante(restauranteAtual));
				// return restauranteAtual;
			 } catch (EntidadeNaoEncontradaException e) {
				throw new NegocioException(e.getMessage());
			}
	}
	
	//atualização parcial 
	/*
	@PatchMapping("/{restauranteId}")
	public RestauranteDTO atualizarParcial(@PathVariable Long restauranteId,
			@RequestBody Map<String, Object> campos, HttpServletRequest request) {
		
		
		Restaurante restauranteAtual = restauranteService.buscarOuFalhar(restauranteId);
		
		merge(campos, restauranteAtual, request);
		
		return atualizar(restauranteId, restauranteAtual);
	}
*/
	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino,
			HttpServletRequest request) {
		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			
			Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
			
			dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
				Field field = org.springframework.util.ReflectionUtils.findField(Restaurante.class, nomePropriedade);
				field.setAccessible(true);
				
				Object novoValor = org.springframework.util.ReflectionUtils.getField(field, restauranteOrigem);
				
				ReflectionUtils.setField(field, restauranteDestino, novoValor);
			});
		} catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
		}
	}
	
	// =========== ATIVACAO INATIVACAO===============================
	
	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarRestaurante (@PathVariable Long id) {
		restauranteService.ativar(id);
	}
	
	@DeleteMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarRestaurante (@PathVariable Long id) {
		restauranteService.inativar(id);
	}
	
	//============ATIVACAO INATIVACAO EM MASSA======================
	
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMassa(@RequestBody List<Long> restauranteIds) {
		try {
		restauranteService.ativarMassa(restauranteIds);
		}catch (RestauranteNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarMassa(@RequestBody List<Long> restauranteIds) {
		try {
		restauranteService.inativarMassa(restauranteIds);
		}catch (RestauranteNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
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
