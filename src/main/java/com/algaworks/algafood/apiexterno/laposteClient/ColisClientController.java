package com.algaworks.algafood.apiexterno.laposteClient;

import java.text.ParseException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/laposteClient")
public class ColisClientController {
	

	@Autowired
	private ColisClient colisClient; 
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ColisClientModel adicionar(@RequestBody @Valid ColisClientInput cateInput) {

			return colisClient.adicionar(cateInput);

	}
	
	@GetMapping("/{id}")
	public ColisClientModel buscar(@PathVariable String id) throws ParseException
	{
	//	categoriaClient.obterDadosToken();
		return colisClient.buscar(id);
	}
	
	@GetMapping
	public ColisClientModel buscar2(@RequestBody ColisClientInput cc) throws ParseException
	{
	//	categoriaClient.obterDadosToken();
		return colisClient.buscar2(cc);
	}

}
