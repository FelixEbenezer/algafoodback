package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
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

import com.algaworks.algafood.api.assembler.FormaPagamentoAssembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoDtoDisassembler;
import com.algaworks.algafood.api.model.FormaPagamentoDTO;
import com.algaworks.algafood.api.model.input.FormaPagamentoInputDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.FormaPagamentoService;

@RestController
@RequestMapping("/formasPagamento")
public class FormaPagamentoController {
	
	@Autowired
	private FormaPagamentoService formaPagamentoService;
	
	@Autowired
	private FormaPagamentoAssembler assembler; 
	
	@Autowired
	private FormaPagamentoDtoDisassembler disassembler; 
	
/*	@GetMapping
	public List<FormaPagamentoDTO> listar () {
		return assembler.toCollectionObject(formaPagamentoService.listarFormaPagamento());
	}*/
	
	@GetMapping
	public ResponseEntity<List<FormaPagamentoDTO>> listar () {
		List<FormaPagamentoDTO> formaPagamentoDTO =  assembler.toCollectionObject(formaPagamentoService.listarFormaPagamento());
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(formaPagamentoDTO);
	}
	
/*	@GetMapping("/{id}")
	public FormaPagamentoDTO buscarPorId(@PathVariable Long id) {
		return assembler.toDTO(formaPagamentoService.buscarOuFalhar(id));
	}*/
	
	@GetMapping("/{id}")
	public ResponseEntity<FormaPagamentoDTO> buscarPorId(@PathVariable Long id) {
		FormaPagamentoDTO formaDTO= assembler.toDTO(formaPagamentoService.buscarOuFalhar(id));
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
				.body(formaDTO);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoDTO adicionar(@RequestBody @Valid FormaPagamentoInputDTO formaPagamentoInputDTO) {
		FormaPagamento formaPagamento = disassembler.toDomainObject(formaPagamentoInputDTO);
		FormaPagamentoDTO formaPagamentoDTO = assembler.toDTO(formaPagamentoService.adicionarFormaPagamento(formaPagamento));
		return formaPagamentoDTO;
	}
	
	@PutMapping("/{id}")
	public FormaPagamentoDTO atualizar (@RequestBody @Valid FormaPagamentoInputDTO formaPagamentoInputDTO, @PathVariable Long id) {
		FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(id);
		disassembler.copyToDomainObject(formaPagamentoInputDTO, formaPagamento);
		return assembler.toDTO(formaPagamentoService.adicionarFormaPagamento(formaPagamento));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover (@PathVariable Long id) {
		formaPagamentoService.removerFormaPagamento(id);
	}

}
