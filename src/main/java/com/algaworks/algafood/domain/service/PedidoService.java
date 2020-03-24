package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;

@Service
public class PedidoService {
	
	private static final String MSG_CODE_UTILISE = "Pedido de código %d não pode ser removida, pois está em uso";

	private static final String MSG_CODE_MAL = "LE CODE %d INSÉRÉ ERRONÉ";


	@Autowired
	private PedidoRepository pedidoRepository; 
	
	@Autowired
	private RestauranteService restauranteService; 
	
	@Autowired
	private FormaPagamentoService formaPagamentoService; 
	
	@Autowired
	private CidadeService cidadeService;
	
	@Autowired
	private UsuarioService usuarioService; 
	
	@Autowired
	private ProdutoService produtoService; 
	
	
	@Transactional
	public List<Pedido> listar() {
		return pedidoRepository.findAll();
	}
	
	
	@Transactional
	public Pedido buscarPorId(Long pedidoId) {
		return buscarOufalhar(pedidoId);
	}
	
	public Pedido buscarOufalhar(Long pedidoId) {
		return pedidoRepository.findById(pedidoId)
				.orElseThrow(()->new PedidoNaoEncontradaException(pedidoId));
	}
	
		
	private void validarPedido(Pedido pedido) {
	    Cidade cidade = cidadeService.buscarOuFalhar(pedido.getEndereco().getCidade().getId());
	    Usuario cliente = usuarioService.buscarOuFalhar(pedido.getCliente().getId());
	    Restaurante restaurante = restauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
	    FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(pedido.getFormaPagamento().getId());
	    
	    pedido.getEndereco().setCidade(cidade);
	    pedido.setCliente(cliente);
	    pedido.setRestaurante(restaurante);
	    pedido.setFormaPagamento(formaPagamento);
	    
	    if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
	        throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
	                formaPagamento.getDescricao()));
	    }
	}
	
	private void validarItens(Pedido pedido) {
	    pedido.getItens().forEach(item -> {
	        Produto produto = produtoService.buscarOuFalhar(
	                pedido.getRestaurante().getId(), item.getProduto().getId());
	        
	        item.setPedido(pedido);
	        item.setProduto(produto);
	        item.setPrecoUnitario(produto.getPreco());
	    });
	}
	
	@Transactional
	public Pedido emitir(Pedido pedido) {
	    validarPedido(pedido);
	    validarItens(pedido);

	    pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
	    pedido.calcularValorTotal();

	    return pedidoRepository.save(pedido);
	}

	

	/*
	 @Transactional
	public Pedido adicionar (Pedido pedido) {
		
		Long restauranteId = pedido.getRestaurante().getId();
		Long formaPagamentoId =  pedido.getFormaPagamento().getId();
		Long cidadeId = pedido.getEndereco().getCidade().getId();
		
		
		
		Optional<Restaurante> restaurante = restauranteRepository.findById(restauranteId);
		Optional<FormaPagamento> formaPagamento = formaPagamentoRepository.findById(formaPagamentoId);
		Optional<Cidade> cidade = cidadeRepository.findById(cidadeId);
		
		if(restaurante.isEmpty()) {
			throw new RestauranteNaoEncontradaException(String.format(MSG_CODE_MAL, restauranteId));
		}
		
		if(formaPagamento.isEmpty()) {
			throw new FormaPagamentoNaoEncontradaException(String.format(MSG_CODE_MAL, formaPagamentoId));
		}
		
		if(cidade.isEmpty()) {
			throw new CidadeNaoEncontradaException(String.format(MSG_CODE_MAL, cidadeId));
		}
		
		pedido.setRestaurante(restaurante.get());
		pedido.setFormaPagamento(formaPagamento.get());
		//pedido.getCliente().setId(null);;
		
		return pedidoRepository.save(pedido);
	}

	 */
	
}
