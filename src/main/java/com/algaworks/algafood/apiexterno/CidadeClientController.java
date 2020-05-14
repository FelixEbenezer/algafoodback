package com.algaworks.algafood.apiexterno;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/cidadesClient")
public class CidadeClientController {
	
	private RestTemplate restTemplate = new RestTemplate();
	
	private CidadeClient cidadeClient = new CidadeClient(
			restTemplate, "https://ngangalixapi.herokuapp.com/");
	
	@GetMapping
	public List<CidadeClientModel> listarCidadeClient() {

		return cidadeClient.listar();

	}

}
