package com.algaworks.algafood.apiexterno.estadoClient;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/estadosClient")
public class EstadoClientController {
	
/*	private RestTemplate restTemplate = new RestTemplate();
	
	private EstadoClient estadoClient = new EstadoClient(
			restTemplate, "https://ngangalixapi.herokuapp.com/");
	
	@GetMapping
	public List<EstadoClientModel> listarCidadeClient() {

		return estadoClient.listar();

	}*/
	
	@Autowired
	private EstadoClient estadoClient; 
	
	@GetMapping
	public List<EstadoClientModel> listarCidadeClient() {

		return estadoClient.listar();

	}
	
	

}
