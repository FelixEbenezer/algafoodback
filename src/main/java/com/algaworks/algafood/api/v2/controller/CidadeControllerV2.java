package com.algaworks.algafood.api.v2.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.v2.assembler.CidadeDtoAssemblerV2;
import com.algaworks.algafood.api.v2.assembler.CidadeDtoDisassemblerV2;
import com.algaworks.algafood.api.v2.model.CidadeDTOV2;
import com.algaworks.algafood.api.v2.model.input.CidadeInputDTOV2;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;

// @CrossOrigin
@RestController
//@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
//@RequestMapping(path = "/cidades", produces = "application/vnd.algafood.v1+json" )
//@RequestMapping(path = "/cidades", produces = AlgaMediaTypes.V2_APPLICATION_JSON_VALUE )
@RequestMapping(path = "/v2/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeControllerV2 {
	
	@Autowired
	private CidadeDtoAssemblerV2 assembler;
	
	@Autowired
	private CidadeDtoDisassemblerV2 disassembler; 
	
	@Autowired
	private CidadeService cidadeService;

	@Deprecated
	@GetMapping
	public CollectionModel<CidadeDTOV2> listar() {
		
			List<Cidade> todasCidades = cidadeService.listarCidade();
			
			return  assembler.toCollectionModel(todasCidades);
			
			}

	@GetMapping("/{id}")
	public CidadeDTOV2 buscarPorId(@PathVariable Long id) {
		
		CidadeDTOV2 cid = assembler.toModel(cidadeService.buscarOuFalhar(id));
		
		return cid; 

	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTOV2 adicionar(@RequestBody @Valid CidadeInputDTOV2 cidadeInputDTO) {
		try {
		Cidade cid = disassembler.toDomainObject(cidadeInputDTO);
		CidadeDTOV2 cidDTO = assembler.toModel(cidadeService.adicionarCidade(cid));
		
		//para criar o link do novo recurso criado...
		
		ResourceUriHelper.addUriInResponseHeader(cidDTO.getIdCidade());
		
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
	public CidadeDTOV2 atualizar (@RequestBody @Valid CidadeInputDTOV2 cidadeInputDTO, @PathVariable Long id) {
		Cidade cid = cidadeService.buscarOuFalhar(id);
		
		disassembler.copyToDomainObject(cidadeInputDTO, cid);
		
		try {
			return assembler.toModel(cidadeService.adicionarCidade(cid));
			} catch (EntidadeNaoEncontradaException e) {
				throw new NegocioException(e.getMessage(), e);
			} 	
	}
	
	//handlerException no APiExceptionHandler

}
