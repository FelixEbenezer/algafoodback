package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.UsuarioDtoAssembler;
import com.algaworks.algafood.api.assembler.UsuarioDtoDisassembler;
import com.algaworks.algafood.api.model.UsuarioDTO;
import com.algaworks.algafood.api.model.input.UsuarioAtualizarInputDTO;
import com.algaworks.algafood.api.model.input.UsuarioInputDTO;
import com.algaworks.algafood.api.model.input.UsuarioSenhaInputDTO;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService; 
	
	@Autowired
	private UsuarioDtoAssembler assembler; 
	
	@Autowired
	private UsuarioDtoDisassembler disassembler; 
	
	@GetMapping
	public List<UsuarioDTO> listar () {
		return assembler.toCollectionObject(usuarioService.listarUsuario());
	}
	
	
	@GetMapping("/{id}")
	public UsuarioDTO buscarPorId(@PathVariable Long id) {
		return assembler.toDTO(usuarioService.buscarOuFalhar(id));
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO adicionar (@RequestBody @Valid UsuarioInputDTO usuarioInputDTO) {
		Usuario usuario = disassembler.toDomainObject(usuarioInputDTO);
		UsuarioDTO usuarioDTO = assembler.toDTO(usuarioService.adicionarUsuario(usuario));
		return usuarioDTO; 
	}
	
	
	@PutMapping("/{id}")
	public UsuarioDTO atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioAtualizarInputDTO usuarioAtualizarInputDTO) {
		Usuario usuario = usuarioService.buscarOuFalhar(id);
		disassembler.copyToDomainObject(usuarioAtualizarInputDTO, usuario);
		return assembler.toDTO(usuarioService.adicionarUsuario(usuario));
	}
	
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover (@PathVariable Long id) {
		usuarioService.removerUsuario(id);
	}
	
	@PutMapping("/{usuarioId}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioSenhaInputDTO senha) {
		usuarioService.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getSenhaNova());
	}


}
