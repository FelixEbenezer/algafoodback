package com.algaworks.algafood.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs;

@Service
public class RestauranteService {
	
	private static final String MSG_CODE_UTILISE = "Cidade de código %d não pode ser removida, pois está em uso";

	private static final String MSG_CODE_MAL = "LE CODE %d INSÉRÉ ERRONÉ";


	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	public CozinhaRepository cozinhaRepository;
	
	@Autowired
	public CidadeRepository cidadeRepository; 
	
	@Autowired
	private FormaPagamentoService formaPagamentoService;
	
	@Autowired
	private UsuarioService usuarioService; 
	
	// LISTAR ==================================
	@Transactional
	public List<Restaurante> listarRestaurante() {
		return restauranteRepository.findAll();
	}
	
	// BUSCAR POR ID ==================================
	@Transactional
	public Optional<Restaurante> bucarPorIdRestaurante (Long id) {
		return restauranteRepository.findById(id);
	}
	
	// ADICIONAR  ==================================
	
	
	@Transactional
	public Restaurante adicionarRestaurante (Restaurante restaurante) {
		
		
		Long cozinhaId = restaurante.getCozinha().getId();
		Long cidadeId = restaurante.getEndereco().getCidade().getId();
		
		Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId); 
		Optional<Cidade> cidade = cidadeRepository.findById(cidadeId);
		
		if( cozinha.isEmpty()) {
			throw new CozinhaEncontradaException(String.format(MSG_CODE_MAL, cozinhaId));
		}
		
		if( cidade.isEmpty()) {
			throw new CidadeNaoEncontradaException(String.format(MSG_CODE_MAL, cidadeId));
		}
		
		restaurante.setCozinha(cozinha.get());
		restaurante.getEndereco().setCidade(cidade.get());
		
		return restauranteRepository.save(restaurante);
		
	}
	
	// REMOVER ==================================
	@Transactional
	public void removerRestaurante (Long id) {
		try {
		restauranteRepository.deleteById(id);
		restauranteRepository.flush();
		}
		
		catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CODE_UTILISE, id));
		}
		
		catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncontradaException(String.format(MSG_CODE_MAL, id));
		}
	}
	
	
	public Restaurante buscarOuFalhar(Long id) {
		return restauranteRepository.findById(id).orElseThrow(
				()-> new RestauranteNaoEncontradaException(id));
	}
	
	// =================ATIVACAO INATIVACAO
	
	@Transactional
	public void ativar (Long id) {
		Restaurante restaurante = buscarOuFalhar(id);
		restaurante.ativar();
	}
	
	@Transactional
	public void inativar (Long id) {
		Restaurante restaurante = buscarOuFalhar(id);
		restaurante.inativar();
	}
	
	//===========ATIVACAO INATIVACAO EM MASSA ================================
	
	@Transactional
	public void ativarMassa(List<Long> restauranteId) {
			// restauranteId.forEach(this::ativar);
			// caso nao quiser usar o forEach como em aula, e como so temos 
		// um unico operacao a fazer, não foi então necessario chamar o map
		// mas chamamos direitamente o forEach no stream
			restauranteId.stream().forEach(id -> ativar(id));
	}
	
	@Transactional
	public void inativarMassa(List<Long> restauranteId) {
			restauranteId.forEach(this::inativar);
	}
	
	//================ ASSOCIAR /DISSOCIAR FORMA PAGAMENTO AO RESTAURANTE======
	
	@Transactional
	public void associar(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);
		
		restaurante.associarFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void dissociar(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);
		
		restaurante.dissociarFormaPagamento(formaPagamento);
	}

	//================ ABERTURA/FECHO DO RESTAURANTE======
	
	@Transactional
	public void abrir(Long id) {
		Restaurante restaurante = buscarOuFalhar(id);
		
		   if(!restaurante.getAtivo()) {
		    	throw new NegocioException(String.format("O restaurante %s está inativo, não dá para abrir lo",
		                restaurante.getNome())); 
		    }
		restaurante.abrir();
	}
	
	@Transactional
	public void fechar(Long id) {
		Restaurante restaurante = buscarOuFalhar(id);
		restaurante.fechar();
	}
	
	//===========ASSOCIAR DISSOCIAR USUARIO AO RESTAURANTE=======================
	
	@Transactional
	public void associarRes (Long restauranteId, Long usuarioId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
		
		restaurante.associarResponsavel(usuario);
	}
	
	@Transactional
	public void dissociarRes (Long restauranteId, Long usuarioId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
		
		restaurante.dissociarResponsavel(usuario);
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
