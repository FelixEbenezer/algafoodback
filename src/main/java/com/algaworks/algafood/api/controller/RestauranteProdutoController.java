package com.algaworks.algafood.api.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.ProdutoDtoAssembler;
import com.algaworks.algafood.api.assembler.ProdutoDtoDisassembler;
import com.algaworks.algafood.api.model.ProdutoDTO;
import com.algaworks.algafood.api.model.input.ProdutoInputDTO;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.ProdutoService;
import com.algaworks.algafood.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
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
	
/*	@GetMapping
	public List<ProdutoDTO> listar (@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
	//	 List<Produto> todosProdutos = produtoRepository.findByRestaurante(restaurante);
		 List<Produto> todosProdutos = produtoRepository.findProdutosAtivosByRestaurante(restaurante);
			     
		return assembler.toCollectionObject(todosProdutos);
	}*/
	
	@GetMapping
	public List<ProdutoDTO> listar (@PathVariable Long restauranteId, @RequestParam (required = false) boolean incluirInativos) {
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		 List<Produto> todosProdutos = new ArrayList<>();
		
		 if(incluirInativos) {
			todosProdutos = produtoRepository.findByRestaurante(restaurante);
		}else {
			todosProdutos = produtoRepository.findProdutosAtivosByRestaurante(restaurante);
		}			     
		return assembler.toCollectionObject(todosProdutos);
	}
	
	
	@GetMapping("/{produtoId}")
	public ProdutoDTO listarProdutoPorRestaurante(@PathVariable Long restauranteId, 
			@PathVariable Long produtoId) {
		// Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		Produto produto = produtoService.buscarOuFalhar(restauranteId, produtoId);
		
		return assembler.toDTO(produto);
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO adicionar (@PathVariable Long restauranteId, @Valid @RequestBody ProdutoInputDTO produtoInputDTO) {
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId); 
		
		// try {
		Produto produto = disassembler.toDomainObject(produtoInputDTO);
		produto.setRestaurante(restaurante);
		return assembler.toDTO(produtoService.salvar(produto));
		//}catch (EntidadeNaoEncontradaException e) {
		//throw new NegocioException(e.getMessage());
		//}
	}
	
	@PutMapping("/{produtoId}")
	public ProdutoDTO atualizar (@PathVariable Long produtoId, 
			                     @PathVariable Long restauranteId, 
			                     @Valid @RequestBody ProdutoInputDTO produtoInputDTO) {
		
		Produto produto = produtoService.buscarOuFalhar(restauranteId, produtoId);
		disassembler.copyToDomainObject(produtoInputDTO, produto);
		produto = produtoService.salvar(produto); 
		
		return assembler.toDTO(produto); 
	}
	
	//=================ATIVAR / DESATIVAR ===============================
	
	@PutMapping("/{produtoId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long produtoId, @PathVariable Long restauranteId) {
		produtoService.ativar(restauranteId, produtoId);
	}
	
	@DeleteMapping("/{produtoId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long produtoId, @PathVariable Long restauranteId) {
		produtoService.desativar(restauranteId, produtoId);
	}

}
