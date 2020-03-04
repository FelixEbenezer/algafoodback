package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

@Service
public class FormaPagamentoService {
	
	private static final String MSG_CODE_UTILISE = "Forma Pagamento de código %d não pode ser removida, pois está em uso";

	private static final String MSG_CODE_MAL = "LE CODE %d INSÉRÉ ERRONÉ";

	
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository; 
	
	@Transactional
	public List<FormaPagamento> listarFormaPagamento() {
		return formaPagamentoRepository.findAll(); 
	}
	
	@Transactional
	public Optional<FormaPagamento> buscarPorId(Long id) {
		return formaPagamentoRepository.findById(id);
	}
	
	@Transactional
	public FormaPagamento adicionarFormaPagamento(FormaPagamento formaPagamento) {
		return formaPagamentoRepository.save(formaPagamento);
	}
	
	
	@Transactional
	public void removerFormaPagamento (Long id) {
		
		try {
			formaPagamentoRepository.deleteById(id);
			formaPagamentoRepository.flush();
		}
		
		catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CODE_UTILISE, id));
		}
		
		catch (EmptyResultDataAccessException e) {
			throw new FormaPagamentoNaoEncontradaException(String.format(MSG_CODE_MAL, id));
		}
		
	}
	
	public FormaPagamento buscarOuFalhar(Long id) {
		return formaPagamentoRepository.findById(id).orElseThrow(()-> new FormaPagamentoNaoEncontradaException(id));
	}

}
