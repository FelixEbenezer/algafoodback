package com.algaworks.algafood.api.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {
	
	@Autowired
	private CidadeService cidadeService;
	
	@GetMapping
	public List<Cidade> listar() {
		return cidadeService.listarCidade();
	}
	
	@GetMapping("/{id}")
	public Cidade buscarPorId(@PathVariable Long id) {
		
		return cidadeService.buscarOuFalhar(id);
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody @Valid Cidade cidade) {
		try {
		Cidade cid = cidadeService.adicionarCidade(cidade);
		return ResponseEntity.status(HttpStatus.CREATED).body(cid);
		}
		
		catch (EntidadeNaoEncontradaException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminiar(@PathVariable Long id) {
		
		cidadeService.removerCidade(id);
		
	}
	
	@PutMapping("/{id}")
	public Cidade atualizar (@RequestBody @Valid Cidade cidade, @PathVariable Long id) {
		Cidade cid = cidadeService.buscarOuFalhar(id);
			BeanUtils.copyProperties(cidade, cid, "id");
			
			try {
			return cidadeService.adicionarCidade(cid);
			//			return cid;
			} catch (EntidadeNaoEncontradaException e) {
				throw new NegocioException(e.getMessage(), e);
			} 	
	}
	
	//handlerException no APiExceptionHandler

}
