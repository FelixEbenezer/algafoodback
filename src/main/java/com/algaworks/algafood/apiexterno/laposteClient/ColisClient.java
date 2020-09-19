package com.algaworks.algafood.apiexterno.laposteClient;

import java.util.Base64;
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
public class ColisClient {
	
	
    String baseURL = "https://api.laposte.fr/suivi/v2/idships/EP111111110FR?lang=fr_FR";
  //  String baseURL = "https://api.laposte.fr/suivi/v2/idships";

    String oauthPath = "/oauth/token";


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
    
    
   
    public static ColisClientModel adicionarColis(ColisClientInput categoria, RestTemplate restTemplate, String url, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Content-Type", "application/json"); 

        HttpEntity<ColisClientInput> request = new HttpEntity<>(categoria, headers);

        ResponseEntity<ColisClientModel> estados = restTemplate
                .exchange(url, HttpMethod.POST, request, ColisClientModel.class);

        return estados.getBody();
    }

     public ColisClientModel adicionar(ColisClientInput categoriaClienteInput) {
        try {
        	String token = obterToken(restTemplate, baseURL + oauthPath);
                ColisClientModel estados = adicionarColis(categoriaClienteInput, restTemplate, baseURL, token);
        			
        	return estados;
        } catch (HttpClientErrorException e) {
            throw new ClientApiException(e.getMessage(), e);
        }
    }
     
     
     //buscarCategoriaPor codigo
     
     public static ColisClientModel buscarCategoria(RestTemplate restTemplate, String url, String token) {
         HttpHeaders headers = new HttpHeaders();
         headers.add("Authorization", "Bearer " + token);

         HttpEntity<Headers> request = new HttpEntity<Headers>(headers);

         ResponseEntity<ColisClientModel> estados = restTemplate
                 .exchange(url, HttpMethod.GET, request, ColisClientModel.class);

         return estados.getBody();
     }
     
     public ColisClientModel buscar(Long categoriaId) {
         try {
     	String token = obterToken(restTemplate, baseURL + oauthPath);
         return buscarCategoria(restTemplate, baseURL + categoriaId, token);
     			
     
         } catch (HttpClientErrorException e) {
             throw new ClientApiException(e.getMessage(), e);
         }
     }
    
        }
    
    