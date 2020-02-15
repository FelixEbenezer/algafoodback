package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class EstadoService {

	private static final String MSG_CODE_UTILISE = "Cidade de código %d não pode ser removida, pois está em uso";

	private static final String MSG_CODE_MAL = "LE CODE %d INSÉRÉ ERRONÉ";

	@Autowired
	EstadoRepository estadoRepository;
	
	public List<Estado> listarEstado() {
		return estadoRepository.findAll();
	}
	
	public Optional<Estado> buscarPorIdEstado(Long id) {
		return estadoRepository.findById(id);
	}
	
	public Estado adicionarEstado (Estado estado) {
		return estadoRepository.save(estado);
	}
	
	public void removerEstado(Long id) {
		try {
			estadoRepository.deleteById(id);
		}
		
		catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CODE_UTILISE, id));
		}
		
		catch (EmptyResultDataAccessException e) {
			throw new EstadoNaoEncontradaException(String.format(MSG_CODE_MAL, id));
		}
	}
	
	public Estado buscarOuFalhar(Long id) {
		return estadoRepository.findById(id).orElseThrow(()-> new EstadoNaoEncontradaException(id));
	}
}
