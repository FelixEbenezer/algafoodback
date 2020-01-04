package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CozinhaService cozinhaService;
	
	
	//@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping
	public List<Cozinha> listarCozinha() {
		return cozinhaRepository.findAll();
	}
	
	
	@PostMapping
	//@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Cozinha> adicionarCozinha(@RequestBody Cozinha cozinha) {
		 Cozinha cozinha1 = cozinhaService.salvar(cozinha);
		 
		 return ResponseEntity.status(HttpStatus.CREATED).body(cozinha1);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cozinha> atualizar (@PathVariable Long id, @RequestBody Cozinha cozinha) {
		
		Optional<Cozinha> cozinhaAtual = cozinhaRepository.findById(id);
		
		if(cozinhaAtual.isPresent()) {
			BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id");
			cozinhaRepository.save(cozinhaAtual.get());
			
			return ResponseEntity.ok(cozinhaAtual.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	
	@DeleteMapping("/{id}")
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
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Cozinha> buscarPorId(@PathVariable Long id) {
		Optional<Cozinha> cozinha =  cozinhaRepository.findById(id);
		
		if( cozinha.isPresent())
		{ return ResponseEntity.status(HttpStatus.OK).body(cozinha.get());}
		
		
		return ResponseEntity.notFound().build(); // para não retornar nada  no body
		
		
	}
	
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
