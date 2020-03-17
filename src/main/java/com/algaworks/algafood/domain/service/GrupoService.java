package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;

@Service
public class GrupoService {
	
	private static final String MSG_CODE_UTILISE = "Grupo de código %d não pode ser removida, pois está em uso";

	private static final String MSG_CODE_MAL = "LE CODE %d INSÉRÉ ERRONÉ";

	
	@Autowired
	private GrupoRepository grupoRepository; 
	
	@Transactional
	public List<Grupo> listarGrupo() {
		return grupoRepository.findAll();
	}
	
	@Transactional
	public Grupo adicionarGrupo (Grupo grupo) {
		return grupoRepository.save(grupo);
	}
	
	@Transactional
	public Optional<Grupo> buscarPorId(Long id) {
		return grupoRepository.findById(id);
	}
	
	@Transactional
	public void remover (Long id) {
		try {
		grupoRepository.deleteById(id);
		grupoRepository.flush();
		} 
		catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CODE_UTILISE, id));
		} catch (EmptyResultDataAccessException e) {
			throw new GrupoNaoEncontradaException(String.format(MSG_CODE_MAL, id));
		}
	}
	
	
	public Grupo buscarOuFalhar(Long id) {
		return grupoRepository.findById(id).orElseThrow(()-> new GrupoNaoEncontradaException(id));
	}

	

}
