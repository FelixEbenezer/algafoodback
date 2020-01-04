package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository; 
	
	public List<Cidade> listarCidade () {
		return cidadeRepository.findAll();
	}
	
	
	public Optional<Cidade> buscarPorIdCidade(Long id) {
		return cidadeRepository.findById(id);
	}
	
	public Cidade adicionarCidade(Cidade cidade) {
		
		Long estadoId = cidade.getEstado().getId();
		
		Optional<Estado> estado = estadoRepository.findById(estadoId);
		
		if(estado.isEmpty()) {
			throw new EntidadeNaoEncontradaException("codigo estado inexistente");
		}
		
		cidade.setEstado(estado.get());
		return cidadeRepository.save(cidade);
	}
	
	
	public void removerCidade (Long id) {
		try{
			cidadeRepository.deleteById(id);
		}
		
		catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException("o codigo da cidade esta em uso");
		}
		
		catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException("codigo cidade inexistente");
		}
		}
	
}
