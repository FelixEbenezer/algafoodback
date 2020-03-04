package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CidadeService {
	
	private static final String MSG_CODE_UTILISE = "Cidade de código %d não pode ser removida, pois está em uso";

	private static final String MSG_CODE_MAL = "LE CODE %d INSÉRÉ ERRONÉ";

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository; 
	
	@Transactional
	public List<Cidade> listarCidade () {
		return cidadeRepository.findAll();
	}
	
	@Transactional
	public Optional<Cidade> buscarPorIdCidade(Long id) {
		return cidadeRepository.findById(id);
	}
	
	@Transactional
	public Cidade adicionarCidade(Cidade cidade) {
		
		Long estadoId = cidade.getEstado().getId();
		
		Optional<Estado> estado = estadoRepository.findById(estadoId);
		
		if(estado.isEmpty()) {
			throw new EstadoNaoEncontradaException(String.format(MSG_CODE_MAL, estadoId));
		}
		
		cidade.setEstado(estado.get());
		return cidadeRepository.save(cidade);
	}
	
	@Transactional
	public void removerCidade (Long id) {
		try{
			cidadeRepository.deleteById(id);
			cidadeRepository.flush();
		}
		
		catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CODE_UTILISE, id));
		}
		
		catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(id);
		}
		}
	
	public Cidade buscarOuFalhar(Long id) {
		return cidadeRepository.findById(id).orElseThrow(
				()-> new CidadeNaoEncontradaException(id));
	}
	
}
