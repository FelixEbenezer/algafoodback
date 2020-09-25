package com.algaworks.algafood.apiexterno.cidadeAlgamoney;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cidadesAlgamoney")
public class CidadeAlgamoneyController {
	

	@Autowired
	private CidadeAlgamoneyClient cidadeAlgamoneyClient; 
	
/*	
	@GetMapping
	public List<CidadeAlgamoneyModel> listarCidadeClient(@RequestParam(required = false) Long estado) {

		return cidadeAlgamoneyClient.listar();

	}
	*/
	
	@GetMapping
	public List<CidadeAlgamoneyModel> listarCidadeClient2(@RequestParam(required = false) Long estado) {

		return cidadeAlgamoneyClient.listar1(estado);

	}
	
	
}
