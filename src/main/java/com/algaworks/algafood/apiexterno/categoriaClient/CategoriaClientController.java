package com.algaworks.algafood.apiexterno.categoriaClient;

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
@RequestMapping("/categoriasClient")
public class CategoriaClientController {
	

	@Autowired
	private CategoriaClient categoriaClient; 
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CategoriaClientModel adicionar(@RequestBody @Valid CategoriaClientInput cateInput) {

			return categoriaClient.adicionar(cateInput);

	}
	
	@GetMapping("/{codigo}")
	public CategoriaClientModel buscar(@PathVariable Long codigo) throws ParseException
	{
	//	categoriaClient.obterDadosToken();
		return categoriaClient.buscar(codigo);
	}

}
