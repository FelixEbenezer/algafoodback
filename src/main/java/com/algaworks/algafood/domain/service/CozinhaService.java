package com.algaworks.algafood.domain.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.CozinhaEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;


@Service
public class CozinhaService {

	private static final String MSG_CODE_UTILISE = "Cidade de código %d não pode ser removida, pois está em uso";

	private static final String MSG_CODE_MAL = "LE CODE %d INSÉRÉ ERRONÉ";

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Cozinha salvar (Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}
	
	
	public void remover (Long id) {
		try {
			cozinhaRepository.deleteById(id);
		}
		
		catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CODE_UTILISE, id));
		}
		
		catch (EmptyResultDataAccessException e) {
			throw new CozinhaEncontradaException(String.format(MSG_CODE_MAL, id));
		}
		
	}
	
	public Cozinha buscarOuFalhar(Long id) {
		return  cozinhaRepository.findById(id).orElseThrow(()
				-> new CozinhaEncontradaException(id));

	}
	
	//consultar por texto completo
		public List<Cozinha> consularPorNome(String nome) {
			return cozinhaRepository.findByNome(nome);
		}
		
		//consultar por uma parte do nome
		public List<Cozinha> consularPorNomeParcial(String nome) {
			return cozinhaRepository.findByNomeContaining(nome);
		}
		
		//consultar por Startin with
			public List<Cozinha> consularPorNomeParcial1(String nome) {
				return cozinhaRepository.findByNomeStartingWith(nome);
			}
		
		//consultar por Ending with
			public List<Cozinha> consularPorNomeParcial2(String nome) {
				return cozinhaRepository.findByNomeEndingWith(nome);
			}

}
