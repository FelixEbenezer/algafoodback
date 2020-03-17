package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.UsuarioAtualizarInputDTO;
import com.algaworks.algafood.api.model.input.UsuarioInputDTO;
import com.algaworks.algafood.api.model.input.UsuarioSenhaInputDTO;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioDtoDisassembler {

	@Autowired
	private ModelMapper modelMapper; 
	
	public Usuario toDomainObject(UsuarioInputDTO usuarioInputDTO) {
		return modelMapper.map(usuarioInputDTO, Usuario.class);
	}
	
	public void copyToDomainObject(UsuarioAtualizarInputDTO usuarioAtualizarInputDTO, Usuario usuario) {
		modelMapper.map(usuarioAtualizarInputDTO, usuario);
	}
	
	public void alterarSenha(UsuarioSenhaInputDTO usuarioSenhaInputDTO, Usuario usuario) {
		if(usuario.getSenha().contains(usuarioSenhaInputDTO.getSenhaAtual())) {
			modelMapper.map(usuarioSenhaInputDTO, usuario);
		};
	}
}
