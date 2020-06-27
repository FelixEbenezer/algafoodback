package com.algaworks.algafood.api.v1.controller;


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
import com.algaworks.algafood.api.v1.assembler.CidadeDtoAssembler;
import com.algaworks.algafood.api.v1.assembler.CidadeDtoDisassembler;
import com.algaworks.algafood.api.v1.model.CidadeDTO;
import com.algaworks.algafood.api.v1.model.input.CidadeInputDTO;
import com.algaworks.algafood.api.v1.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;

// @CrossOrigin
@RestController
//@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
//@RequestMapping(path = "/cidades", produces = "application/vnd.algafood.v1+json" )
//@RequestMapping(path = "/cidades", produces = AlgaMediaTypes.V1_APPLICATION_JSON_VALUE )
@RequestMapping(path = "/v1/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {
	
	@Autowired
	private CidadeDtoAssembler assembler;
	
	@Autowired
	private CidadeDtoDisassembler disassembler; 
	
	@Autowired
	private CidadeService cidadeService;

	
/*	@GetMapping
	public List<CidadeDTO> listar() {
		return assembler.toCollectionObject(cidadeService.listarCidade());
	}*/
	
	@GetMapping
	public CollectionModel<CidadeDTO> listar() {
		
			List<Cidade> todasCidades = cidadeService.listarCidade();
			
			return  assembler.toCollectionModel(todasCidades);
			
		/*	cidadesModel.forEach(cidadeModel -> {
				cidadeModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class)
						.buscarPorId(cidadeModel.getId())).withSelfRel());
				
				cidadeModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class)
						.listar()).withRel("cidades"));
				
				cidadeModel.getEstado().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstadoController.class)
						.buscarPorId(cidadeModel.getEstado().getId())).withSelfRel());
			});
			CollectionModel<CidadeDTO> cidadesCollectionModel = new CollectionModel<>(cidadesModel);
			cidadesCollectionModel.add(WebMvcLinkBuilder.linkTo(CidadeController.class).withSelfRel());
			return cidadesCollectionModel;*/
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
		
		CidadeDTO cid = assembler.toModel(cidadeService.buscarOuFalhar(id));
		
		return cid; 

/*		//	cid.add(new Link("localhost:8080/cidades/1"));
		// cid.add(WebMvcLinkBuilder.linkTo(CidadeController.class).slash(cid.getId()).withSelfRel());
		cid.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class).buscarPorId(cid.getId())).withSelfRel());
		
//		cidadeModel.add(new Link("http://api.algafood.local:8080/cidades", "cidades"));
		cid.add(WebMvcLinkBuilder.linkTo(CidadeController.class)
				.withRel("cidades"));
		
//		cid.getEstado().add(new Link("localhost:8080/estados/1"));
		cid.getEstado().add(WebMvcLinkBuilder.linkTo(EstadoController.class)
				.slash(cid.getEstado().getId()).withSelfRel());
	*/		
		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO adicionar(@RequestBody @Valid CidadeInputDTO cidadeInputDTO) {
		try {
		Cidade cid = disassembler.toDomainObject(cidadeInputDTO);
		CidadeDTO cidDTO = assembler.toModel(cidadeService.adicionarCidade(cid));
		
		//para criar o link do novo recurso criado...
		
		ResourceUriHelper.addUriInResponseHeader(cidDTO.getId());
		
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
			return assembler.toModel(cidadeService.adicionarCidade(cid));
			} catch (EntidadeNaoEncontradaException e) {
				throw new NegocioException(e.getMessage(), e);
			} 	
	}
	
	//handlerException no APiExceptionHandler

}
