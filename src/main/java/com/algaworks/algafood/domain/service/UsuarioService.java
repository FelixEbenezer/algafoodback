package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradaException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	private static final String MSG_CODE_UTILISE = "Usuario de código %d não pode ser removida, pois está em uso";

	private static final String MSG_CODE_MAL = "LE CODE %d INSÉRÉ ERRONÉ";


	@Autowired
	private UsuarioRepository usuarioRepository; 
	
	@Transactional
	public List<Usuario> listarUsuario() {
		return usuarioRepository.findAll();
	}
	
	@Transactional
	public Optional<Usuario> buscarPorId(Long id) {
		return usuarioRepository.findById(id);
	}
	
	@Transactional
	public Usuario adicionarUsuario (Usuario usuario) {
		
		usuarioRepository.detach(usuario);
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
		
		if(usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException(String.format("ja existe alguém com sgte email %s", usuario.getEmail()));
		}
		return usuarioRepository.save(usuario);
	}
	
	
	@Transactional
	public void removerUsuario (Long id) {
		try {
		usuarioRepository.deleteById(id);
		usuarioRepository.flush();
		}
		catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CODE_UTILISE, id));
		}
		catch (EmptyResultDataAccessException e) {
			throw new UsuarioNaoEncontradaException(String.format(MSG_CODE_MAL, id));
		}
	}
	
	
	@Transactional
	public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
		Usuario usuario = buscarOuFalhar(usuarioId);
		
		if (usuario.senhaNaoCoincideCom(senhaAtual)) {
			throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
		}
		
		usuario.setSenha(novaSenha);
	}
	
	
	public Usuario buscarOuFalhar(Long id) {
		return usuarioRepository.findById(id).orElseThrow(()-> new UsuarioNaoEncontradaException(id));

	}
}
