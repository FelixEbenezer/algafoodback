package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.algaworks.algafood.api.assembler.CozinhaDtoAssembler;
import com.algaworks.algafood.api.assembler.CozinhaDtoDisassembler;
import com.algaworks.algafood.api.model.CozinhaDTO;
import com.algaworks.algafood.api.model.input.CozinhaInputDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {
	
	@Autowired
	private CozinhaDtoAssembler assembler; 
	
	@Autowired
	private CozinhaDtoDisassembler disassembler; 
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CozinhaService cozinhaService;
	
// ========================LISTAR ===============================================	
	//antes DTO
/*	//@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping
	public List<Cozinha> listarCozinha() {
		return cozinhaRepository.findAll();
	}
	*/
	
	// com DTO
	@GetMapping
	public List<CozinhaDTO> listarCozinha() {
		return assembler.toCollectionDTO(cozinhaRepository.findAll());
	}
// ===================== FIN listar ==============================================
	
	
// ===================== ADICIONAR ===============================================
	// antes do DTO
/*	@PostMapping
	//@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Cozinha> adicionarCozinha(@RequestBody @Valid Cozinha cozinha) {
		 Cozinha cozinha1 = cozinhaService.salvar(cozinha);
		 
		 return ResponseEntity.status(HttpStatus.CREATED).body(cozinha1);
	}*/
	
	// com DTO
	@PostMapping
	//@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<CozinhaDTO> adicionarCozinha(@RequestBody @Valid CozinhaInputDTO cozinhaInputDTO) {
		 Cozinha cozinha1 = disassembler.toDomainObject(cozinhaInputDTO);
		 CozinhaDTO coz = assembler.toDTO(cozinhaService.salvar(cozinha1));
		 
 
		 return ResponseEntity.status(HttpStatus.CREATED).body(coz);
	}

// ================== FIN ADICIONAR ==============================================
	
	
//=============ATUALIZAR=======================================================
// VERSAO ANTIGA============================
/*	
	@PutMapping("/{id}")
	public ResponseEntity<Cozinha> atualizar (@PathVariable Long id, @RequestBody Cozinha cozinha) {
		
		Optional<Cozinha> cozinhaAtual = cozinhaRepository.findById(id);
		
		if(cozinhaAtual.isPresent()) {
			BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id");
			cozinhaRepository.save(cozinhaAtual.get());
			
			return ResponseEntity.ok(cozinhaAtual.get());
		}
		
		return ResponseEntity.notFound().build();
	}*/

// VERSÃO SIMPLIFFCADA=================

// sem DTO

   /*	@PutMapping("/{id}")
	public Cozinha atualizar (@PathVariable Long id, @RequestBody @Valid Cozinha cozinha) {
		
		Cozinha cozinhaAtual = cozinhaService.buscarOuFalhar(id);
		BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
		cozinhaRepository.save(cozinhaAtual);
				
		return cozinhaAtual;
	}*/
	
	// com DTO
	
	@PutMapping("/{id}")
	public CozinhaDTO atualizar (@PathVariable Long id, @RequestBody @Valid CozinhaInputDTO cozinhaInputDTO) {
		
		Cozinha cozinhaAtual = cozinhaService.buscarOuFalhar(id);
		disassembler.copyToDomainObject(cozinhaInputDTO, cozinhaAtual);
		return assembler.toDTO(cozinhaRepository.save(cozinhaAtual));
	}
////==============================================================================
	

	
	
	
///========= METODO REMOVER ======================================
// A PRIMEIRA VERSAO =======================

/*	@DeleteMapping("/{id}")
	public ResponseEntity<Cozinha> remover (@PathVariable Long id){
		try {
		
		cozinhaService.remover(id);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		
		catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
		
		catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}*/
	
// A VERSÃO SIMPLIFICADA =============================================
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover (@PathVariable Long id){
		cozinhaService.remover(id);
	}
	
//================================================================================
	
	
//==========================BUSCAR POR ID
// VERSÃO PRIMARIA
/*	@GetMapping("/{id}")
	public ResponseEntity<Cozinha> buscarPorId(@PathVariable Long id) {
		Optional<Cozinha> cozinha =  cozinhaRepository.findById(id);
		
		if( cozinha.isPresent())
		{ return ResponseEntity.status(HttpStatus.OK).body(cozinha.get());}
		
		
		return ResponseEntity.notFound().build(); // para não retornar nada  no body
		
		
	}*/

	// VERSAO SIMPLIFICADA
	
	// sem DTO
/*	@GetMapping("/{id}")
	public Cozinha buscarPorId(@PathVariable Long id) {
		return  cozinhaService.buscarOuFalhar(id);
		
	}*/
	
	// com DTO
	
	@GetMapping("/{id}")
	public CozinhaDTO buscarPorId(@PathVariable Long id) {
		return  assembler.toDTO(cozinhaService.buscarOuFalhar(id));
		
	}
//===============================================================================
	
	
	
	
	//consultar por nome completo
	
		@GetMapping("/por-nome")
		public List<Cozinha> consultar(String nome) {
			return cozinhaService.consularPorNome(nome);
		}
		
		//consultar por nome completo
		
		@GetMapping("/por-nome-parcial")
		public List<Cozinha> consultarParcial(String nome) {
			return cozinhaService.consularPorNomeParcial(nome);
		}
		
		//consultar starting with
		
		@GetMapping("/por-nome-parcial1")
		public List<Cozinha> consultarParcial1(String nome) {
			return cozinhaService.consularPorNomeParcial1(nome);
		}
		
		//consultar starting with
		
		@GetMapping("/por-nome-parcial2")
		public List<Cozinha> consultarParcial2(String nome) {
			return cozinhaService.consularPorNomeParcial2(nome);
		}
				
		

}
