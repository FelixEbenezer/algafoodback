package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.model.CidadeDTO;
import com.algaworks.algafood.domain.model.Cidade;

/*
@Component
public class CidadeDtoAssembler {

	@Autowired
	private ModelMapper modelMapper; 
	
	public CidadeDTO toDTO(Cidade cidade) {
		return modelMapper.map(cidade, CidadeDTO.class); 
	}
	
	public List<CidadeDTO> toCollectionObject(List<Cidade> cidades) {
		return cidades.stream().map(cidade -> toDTO(cidade))
				.collect(Collectors.toList());
	}
}
*/

@Component
public class CidadeDtoAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeDTO> {

	public CidadeDtoAssembler() {
		super(CidadeController.class, CidadeDTO.class);
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private ModelMapper modelMapper; 
	
/*	public CidadeDTO toDTO(Cidade cidade) {
		return modelMapper.map(cidade, CidadeDTO.class); 
	}
	
	public List<CidadeDTO> toCollectionObject(List<Cidade> cidades) {
		return cidades.stream().map(cidade -> toDTO(cidade))
				.collect(Collectors.toList());
	}*/

	@Override
	public CidadeDTO toModel(Cidade cidade) {
		CidadeDTO cidadeDTO = createModelWithId(cidade.getId(), cidade);
		modelMapper.map(cidade, cidadeDTO);
		
		cidadeDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class)
				.listar()).withRel("cidades"));
		
		cidadeDTO.getEstado().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstadoController.class)
				.buscarPorId(cidadeDTO.getEstado().getId())).withSelfRel());

		
		return cidadeDTO;
	}
	
	@Override
	public CollectionModel<CidadeDTO> toCollectionModel(Iterable<? extends Cidade> entities) {
		// TODO Auto-generated method stub
		return super.toCollectionModel(entities)
					.add(WebMvcLinkBuilder.linkTo(CidadeController.class).withSelfRel());
	}
}

