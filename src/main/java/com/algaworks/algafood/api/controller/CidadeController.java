package com.algaworks.algafood.api.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.CidadeDtoAssembler;
import com.algaworks.algafood.api.assembler.CidadeDtoDisassembler;
import com.algaworks.algafood.api.model.CidadeDTO;
import com.algaworks.algafood.api.model.input.CidadeInputDTO;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;

// @CrossOrigin
@RestController
@RequestMapping("/cidades")
public class CidadeController {
	
	@Autowired
	private CidadeDtoAssembler assembler;
	
	@Autowired
	private CidadeDtoDisassembler disassembler; 
	
	@Autowired
	private CidadeService cidadeService;

	
	@GetMapping
	public List<CidadeDTO> listar() {
		return assembler.toCollectionObject(cidadeService.listarCidade());
	}

	
/*	@GetMapping
	public ResponseEntity<List<CidadeDTO>> listar() {
		List<CidadeDTO> cidadeDTO = assembler.toCollectionObject(cidadeService.listarCidade());
	 
		return ResponseEntity.ok()
					.header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:8000")
					.body(cidadeDTO);
	}*/
	
	
	@GetMapping("/{id}")
	public CidadeDTO buscarPorId(@PathVariable Long id) {
		
		return assembler.toDTO(cidadeService.buscarOuFalhar(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO adicionar(@RequestBody @Valid CidadeInputDTO cidadeInputDTO) {
		try {
		Cidade cid = disassembler.toDomainObject(cidadeInputDTO);
		CidadeDTO cidDTO = assembler.toDTO(cidadeService.adicionarCidade(cid));
		return cidDTO; //ResponseEntity.status(HttpStatus.CREATED).body(cidDTO);
		}
		
		catch (EntidadeNaoEncontradaException e) {
		//return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		throw new NegocioException(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminiar(@PathVariable Long id) {
		
		cidadeService.removerCidade(id);
		
	}
	
	@PutMapping("/{id}")
	public CidadeDTO atualizar (@RequestBody @Valid CidadeInputDTO cidadeInputDTO, @PathVariable Long id) {
		Cidade cid = cidadeService.buscarOuFalhar(id);
		
		disassembler.copyToDomainObject(cidadeInputDTO, cid);
		
		try {
			return assembler.toDTO(cidadeService.adicionarCidade(cid));
			} catch (EntidadeNaoEncontradaException e) {
				throw new NegocioException(e.getMessage(), e);
			} 	
	}
	
	//handlerException no APiExceptionHandler

}
