package com.algaworks.algafood.apiexterno.estadoClient;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;

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

import com.algaworks.algafood.apiexterno.problem.ClientApiException;
import com.sun.net.httpserver.Headers;

@Component
public class PessoaClient {
	
	
    String baseURL = "https://ngangalixapi.herokuapp.com";
    String oauthPath = "/oauth/token";
    String estadosPath = "/pessoas";

    RestTemplate restTemplate = new RestTemplate();


	
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
         eliminarEstados(restTemplate, baseURL + estadosPath + estadoId, token);
    			
    
        } catch (HttpClientErrorException e) {
            throw new ClientApiException(e.getMessage(), e);
        }
    }
	
}

