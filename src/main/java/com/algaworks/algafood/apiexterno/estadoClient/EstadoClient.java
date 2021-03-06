package com.algaworks.algafood.apiexterno.estadoClient;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.algaworks.algafood.api.v1.assembler.EstadoDtoAssembler;
import com.algaworks.algafood.api.v1.model.EstadoDTO;
import com.algaworks.algafood.apiexterno.problem.ClientApiException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.EstadoService;
import com.sun.net.httpserver.Headers;

@Component
public class EstadoClient {
	
	
    String baseURL = "https://ngangalixapi.herokuapp.com";
    String oauthPath = "/oauth/token";
    String estadosPath = "/estados";

    RestTemplate restTemplate = new RestTemplate();
    
    
    @Autowired
    private EstadoService estadoService;
    
    @Autowired
    private EstadoDtoAssembler estadoDtoAssembler;

    @Autowired
    private EstadoClientModelDisassembler estadoClientModelDisassembler;

	
    public String obterToken(RestTemplate restTemplate, String url) {
        byte[] authData = "angular:angu".getBytes();
        String encodedAuthData = new String(Base64.getEncoder().encode(authData));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + encodedAuthData);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", "f@g.com");
        params.add("password", "admin");
        params.add("grant_type", "password");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        Map<String, String> response = restTemplate.postForObject(url, request, Map.class);

        return response.get("access_token");
    }

    //obterEstados
    public static EstadoClientModel[] obterEstados(RestTemplate restTemplate, String url, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        HttpEntity<Headers> request = new HttpEntity<Headers>(headers);

        ResponseEntity<EstadoClientModel[]> estados = restTemplate
                .exchange(url, HttpMethod.GET, request, EstadoClientModel[].class);

        return estados.getBody();
    }
    
    public List<EstadoClientModel> listar() {
        try {
    	String token = obterToken(restTemplate, baseURL + oauthPath);
            EstadoClientModel[] estados = obterEstados(restTemplate, baseURL + estadosPath, token);
    			
    	return Arrays.asList(estados);
        } catch (HttpClientErrorException e) {
            throw new ClientApiException(e.getMessage(), e);
        }
    }
    
    //eliminarEstados
    public static void eliminarEstados(RestTemplate restTemplate, String url, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        HttpEntity<Headers> request = new HttpEntity<Headers>(headers);

        ResponseEntity<EstadoClientModel[]> estados = restTemplate
                .exchange(url, HttpMethod.DELETE, request, EstadoClientModel[].class);

        estados.getBody();
    }
    
    public void eliminar(Long estadoId) {
        try {
    	String token = obterToken(restTemplate, baseURL + oauthPath);
         eliminarEstados(restTemplate, baseURL + estadosPath +estadoId, token);
    			
    
        } catch (HttpClientErrorException e) {
            throw new ClientApiException(e.getMessage(), e);
        }
    }
    
    
    
    public List<EstadoDTO> sincronizarNovosEstados() {
        List<Long> idEstadosCadastrados = estadoService.listarEstado().stream()
                .map(Estado::getId)
                .collect(Collectors.toList());

        // Considere o filtro que implementei como um exemplo apenas.
        // A idéia é indicar que é necessária uma lógica para evitar duplicidade. 
        // O código é auto-incremental e provavelmente o esse filtro não vai fazer sentido. 
        // Verifique como pode identificar os estados como únicos e ajuste conforme sua necessidade.
        return listar().stream()
                .filter(estado -> !idEstadosCadastrados.contains(estado.getCodigo()))
                .map(estadoClientModelDisassembler::toDomainObject)
                .map(estadoService::adicionarEstado)
                .map(estadoDtoAssembler::toDTO)
                .collect(Collectors.toList());
    }
    
    

  
	
// private static final String RESOURCE_PATH = "/estados";
	// private String url;
//	private RestTemplate restTemplate;
	
	 //   String token = obterToken(restTemplate, baseURL + oauthPath);
	  //  Map<String, Object> estados = obterEstados(restTemplate, baseURL + estadosPath, token);

	
	
		
		
	/*	public EstadoClient(RestTemplate restTemplate, String url) {

			this.restTemplate = restTemplate;
			this.url = url;
		}*/
	
    
/*	public List<EstadoClientModel> listar() {
		try {
			URI resourceUri = URI.create(url + RESOURCE_PATH);
			
			EstadoClientModel[] estados = restTemplate
					.getForObject(resourceUri, EstadoClientModel[].class);
			
			return Arrays.asList(estados);
		} catch (HttpClientErrorException e) {
            throw new ClientApiException(e.getMessage(), e);
        }
	}*/
	
}

