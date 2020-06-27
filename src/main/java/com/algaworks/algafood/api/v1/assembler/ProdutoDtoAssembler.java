package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.v1.model.ProdutoDTO;
import com.algaworks.algafood.domain.model.Produto;

@Component
public class ProdutoDtoAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoDTO> {

	@Autowired
	private ModelMapper modelMapper; 
	
	@Autowired
	private AlgaLinks algaLinks; 
	
	public ProdutoDtoAssembler() {
		super(RestauranteProdutoController.class, ProdutoDTO.class);
	}
	
	@Override
    public ProdutoDTO toModel(Produto produto) {
        ProdutoDTO produtoModel = createModelWithId(
                produto.getId(), produto, produto.getRestaurante().getId());
        
        modelMapper.map(produto, produtoModel);
        
        produtoModel.add(algaLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));
        
        produtoModel.add(algaLinks.linkToFotoProduto(
                produto.getRestaurante().getId(), produto.getId(), "foto"));

        
        return produtoModel;
    }   
	
	@Override
	public CollectionModel<ProdutoDTO> toCollectionModel(Iterable<? extends Produto> entities) {
		// TODO Auto-generated method stub
		return super.toCollectionModel(entities);
	}
	
	/*
	public ProdutoDTO toDTO(Produto produto) {
		return modelMapper.map(produto, ProdutoDTO.class);
	}
	
	public List<ProdutoDTO> toCollectionObject(Collection<Produto> produtos){
		return produtos.stream().map(produto -> toDTO(produto))
				.collect(Collectors.toList());
	}*/
}
