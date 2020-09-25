package com.algaworks.algafood.api.v1.assembler;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.controller.UsuarioController;
import com.algaworks.algafood.api.v1.controller.UsuarioGrupoController;
import com.algaworks.algafood.api.v1.model.UsuarioDTO;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioDtoAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioDTO> {

	public UsuarioDtoAssembler() {
		super(UsuarioController.class, UsuarioDTO.class);
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private ModelMapper modelMapper; 
	
    @Override
    public UsuarioDTO toModel(Usuario usuario) {
        UsuarioDTO usuarioModel = createModelWithId(usuario.getId(), usuario);
  //      usuarioModel.setNomeEmail(mountNomEmailView(usuarioModel.getNome()));
        modelMapper.map(usuario, usuarioModel);
        
        usuarioModel.add(WebMvcLinkBuilder.linkTo(UsuarioController.class).withRel("usuarios"));
        
        usuarioModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioGrupoController.class)
                .listar(usuario.getId())).withRel("grupos-usuario"));
        
        return usuarioModel;
    }
    
    /*
    public String mountNomEmailView(Usuario usuario) {
		return usuario.getNome()+ ", " + usuario.getEmail();
	}*/
    
    @Override
    public CollectionModel<UsuarioDTO> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities)
            .add(WebMvcLinkBuilder.linkTo(UsuarioController.class).withSelfRel());
    }   

	
	
/*	public UsuarioDTO toDTO (Usuario usuario) {
		return modelMapper.map(usuario, UsuarioDTO.class);
	}
	
	public List<UsuarioDTO> toCollectionObject(Collection<Usuario> usuarios ) {
		return usuarios.stream().map(usuario -> toDTO(usuario))
				.collect(Collectors.toList());
	}*/
}
