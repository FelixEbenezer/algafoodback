package com.algaworks.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	
	public Produto buscarOuFalhar(Long produtoId, Long restauranteId) {
		return produtoRepository.findById(produtoId, restauranteId)
				.orElseThrow(()-> new ProdutoNaoEncontradaException(restauranteId,produtoId));

	}
	
	@Transactional
	public Produto salvar (Produto produto) {
		//Restaurante restaurante = new Restaurante();
		
		produtoRepository.detach(produto);
		// restauranteRepository.detach(restaurante);
		
		Optional<Produto> produtoExistente = produtoRepository.findByNome(produto.getNome());
		
		if(produtoExistente.isPresent() && !produtoExistente.get().equals(produto) && produtoExistente.get().getRestaurante().getId().equals(produto.getRestaurante().getId()) )
		{
			throw new NegocioException(String.format("O produto com este nome %s já existe", produto.getNome()));
		}
		return produtoRepository.save(produto);
	} 
	
	@Transactional
	public void ativar(Long produtoId, Long restauranteId) {
		Produto produto = buscarOuFalhar(produtoId, restauranteId);
		produto.ativar();
	}
	
	@Transactional
	public void desativar(Long produtoId, Long restauranteId) {
		Produto produto = buscarOuFalhar(produtoId, restauranteId);
			produto.inativar();
	}
}
