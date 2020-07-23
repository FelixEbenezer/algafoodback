package com.algaworks.algafood.api.v1.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

import com.algaworks.algafood.api.v1.assembler.UsuarioDtoAssembler;
import com.algaworks.algafood.api.v1.assembler.UsuarioDtoDisassembler;
import com.algaworks.algafood.api.v1.model.UsuarioDTO;
import com.algaworks.algafood.api.v1.model.input.UsuarioAtualizarInputDTO;
import com.algaworks.algafood.api.v1.model.input.UsuarioInputDTO;
import com.algaworks.algafood.api.v1.model.input.UsuarioSenhaInputDTO;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.UsuarioService;

@RestController
@RequestMapping("/v1/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService; 
	
	@Autowired
	private UsuarioDtoAssembler assembler; 
	
	@Autowired
	private UsuarioDtoDisassembler disassembler; 
	
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@GetMapping
	public CollectionModel<UsuarioDTO> listar () {
		return assembler.toCollectionModel(usuarioService.listarUsuario());
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@GetMapping("/{id}")
	public UsuarioDTO buscarPorId(@PathVariable Long id) {
		return assembler.toModel(usuarioService.buscarOuFalhar(id));
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO adicionar (@RequestBody @Valid UsuarioInputDTO usuarioInputDTO) {
		Usuario usuario = disassembler.toDomainObject(usuarioInputDTO);
		//UsuarioDTO usuarioDTO = assembler.toModel(usuarioService.adicionarUsuario(usuario));
		UsuarioDTO usuarioDTO = assembler.toModel(usuarioService.salvarIncluindoEmGrupoPadrao(usuario));
		return usuarioDTO; 
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@PutMapping("/{id}")
	public UsuarioDTO atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioAtualizarInputDTO usuarioAtualizarInputDTO) {
		Usuario usuario = usuarioService.buscarOuFalhar(id);
		disassembler.copyToDomainObject(usuarioAtualizarInputDTO, usuario);
		return assembler.toModel(usuarioService.adicionarUsuario(usuario));
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover (@PathVariable Long id) {
		usuarioService.removerUsuario(id);
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@PutMapping("/{usuarioId}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioSenhaInputDTO senha) {
		usuarioService.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getSenhaNova());
	}
 

}
