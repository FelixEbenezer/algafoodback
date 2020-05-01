package com.algaworks.algafood.apiexterno;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

//@Service
public class CidadeClient {
	
private static final String RESOURCE_PATH = "/cidades";
	
	private RestTemplate restTemplate;
	private String url;
	
	
	
	public CidadeClient(RestTemplate restTemplate, String url) {

		this.restTemplate = restTemplate;
		this.url = url;
	}



	public List<CidadeClientModel> listar() {
	//	try {
			URI resourceUri = URI.create(url + RESOURCE_PATH);
			
			CidadeClientModel[] cidades = restTemplate
					.getForObject(resourceUri, CidadeClientModel[].class);
			
			return Arrays.asList(cidades);
	//	} catch (RestClientResponseException e) {
		//	throw new ClientApiException(e.getMessage(), e);
		//}
	}


}