package com.algaworks.algafood.api.v1.controller;


import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.assembler.ProdutoDtoAssembler;
import com.algaworks.algafood.api.v1.assembler.ProdutoDtoDisassembler;
import com.algaworks.algafood.api.v1.model.ProdutoDTO;
import com.algaworks.algafood.api.v1.model.input.ProdutoInputDTO;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.ProdutoService;
import com.algaworks.algafood.domain.service.RestauranteService;

@RestController
@RequestMapping("/v1/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {
	
	@Autowired
	private RestauranteService restauranteService; 
	
	@Autowired
	private ProdutoService produtoService; 
	
	@Autowired
	private ProdutoDtoAssembler assembler; 
	
	@Autowired
	private ProdutoDtoDisassembler disassembler; 
	
	@Autowired
	private ProdutoRepository produtoRepository; 
	
	@Autowired
	private AlgaLinks algaLinks; 
	
/*	@GetMapping
	public List<ProdutoDTO> listar (@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
	//	 List<Produto> todosProdutos = produtoRepository.findByRestaurante(restaurante);
		 List<Produto> todosProdutos = produtoRepository.findProdutosAtivosByRestaurante(restaurante);
			     
		return assembler.toCollectionObject(todosProdutos);
	}*/
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping
	public CollectionModel<ProdutoDTO> listar (@PathVariable Long restauranteId, @RequestParam (required = false) Boolean incluirInativos) {
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		// List<Produto> todosProdutos = new ArrayList<>();
		List<Produto> todosProdutos = null;
		
		 if(incluirInativos) {
			todosProdutos = produtoRepository.findByRestaurante(restaurante);
		}else {
			todosProdutos = produtoRepository.findProdutosAtivosByRestaurante(restaurante);
		}			     
		return assembler.toCollectionModel(todosProdutos)
				.add(algaLinks.linkToProdutos(restauranteId));
	}
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping("/{produtoId}")
	public ProdutoDTO listarProdutoPorRestaurante(@PathVariable Long restauranteId, 
			@PathVariable Long produtoId) {
		// Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		Produto produto = produtoService.buscarOuFalhar(restauranteId, produtoId);
		
		return assembler.toModel(produto);
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO adicionar (@PathVariable Long restauranteId, @Valid @RequestBody ProdutoInputDTO produtoInputDTO) {
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId); 
		
		// try {
		Produto produto = disassembler.toDomainObject(produtoInputDTO);
		produto.setRestaurante(restaurante);
		return assembler.toModel(produtoService.salvar(produto));
		//}catch (EntidadeNaoEncontradaException e) {
		//throw new NegocioException(e.getMessage());
		//}
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PutMapping("/{produtoId}")
	public ProdutoDTO atualizar (@PathVariable Long produtoId, 
			                     @PathVariable Long restauranteId, 
			                     @Valid @RequestBody ProdutoInputDTO produtoInputDTO) {
		
		Produto produto = produtoService.buscarOuFalhar(restauranteId, produtoId);
		disassembler.copyToDomainObject(produtoInputDTO, produto);
		produto = produtoService.salvar(produto); 
		
		return assembler.toModel(produto); 
	}
	
	//=================ATIVAR / DESATIVAR ===============================
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PutMapping("/{produtoId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long produtoId, @PathVariable Long restauranteId) {
		produtoService.ativar(restauranteId, produtoId);
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@DeleteMapping("/{produtoId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long produtoId, @PathVariable Long restauranteId) {
		produtoService.desativar(restauranteId, produtoId);
	}

}
