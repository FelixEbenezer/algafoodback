package com.algaworks.algafood.apiexterno.categoriaClient;

import java.text.ParseException;
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
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jose.util.JSONObjectUtils;
import com.sun.net.httpserver.Headers;

@Component
public class CategoriaClient {
	
	
    String baseURL = "https://ngangalixapi.herokuapp.com";
    String oauthPath = "/oauth/token";
    String estadosPath = "/categorias/";

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
    
    
    public Map<String, Object> obterDadosToken() throws ParseException {
        // O JWT é dividido em 3 partes, sendo Header, Payload e Signature
        // Queremos apenas o Payload, então dividimos a string nos pontos e pegamos a segunda parte
    	String token = obterToken(restTemplate, baseURL + oauthPath);
        String tokenPayload = token.split("\\.")[1];
        System.out.println(tokenPayload);
        System.out.println("======================================================");
        // O token é um json codificado em Base64, então o decodificamos
        // Importe com.nimbusds.jose.util.Base64URL
        String decodedPayload = new String(Base64URL.from(tokenPayload).decode());
        System.out.println(decodedPayload);
        // Importe com.nimbusds.jose.util.JSONObjectUtils
        return JSONObjectUtils.parse(decodedPayload);
    }
    
    

/*    //obterEstados
    public static CategoriaClientModel adicionarCategorias(RestTemplate restTemplate, String url, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        HttpEntity<Headers> request = new HttpEntity<Headers>(headers);

        ResponseEntity<CategoriaClientModel> estados = restTemplate
                .exchange(url, HttpMethod.POST, request, CategoriaClientModel.class);

        return estados.getBody();
    }
    
    public CategoriaClientModel adicionar(String nome) {
        try {
    	String token = obterToken(restTemplate, baseURL + oauthPath);
            CategoriaClientModel estados = adicionarCategorias(restTemplate, baseURL + estadosPath, token);
    			
    	return estados;
        } catch (HttpClientErrorException e) {
            throw new ClientApiException(e.getMessage(), e);
        }
    }*/
    
    
    public static CategoriaClientModel adicionarCategorias(CategoriaClientInput categoria, RestTemplate restTemplate, String url, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Content-Type", "application/json"); 

        HttpEntity<CategoriaClientInput> request = new HttpEntity<>(categoria, headers);

        ResponseEntity<CategoriaClientModel> estados = restTemplate
                .exchange(url, HttpMethod.POST, request, CategoriaClientModel.class);

        return estados.getBody();
    }

     public CategoriaClientModel adicionar(CategoriaClientInput categoriaClienteInput) {
        try {
        	String token = obterToken(restTemplate, baseURL + oauthPath);
                CategoriaClientModel estados = adicionarCategorias(categoriaClienteInput, restTemplate, baseURL + estadosPath, token);
        			
        	return estados;
        } catch (HttpClientErrorException e) {
            throw new ClientApiException(e.getMessage(), e);
        }
    }
     
     
     //buscarCategoriaPor codigo
     
     public static CategoriaClientModel buscarCategoria(RestTemplate restTemplate, String url, String token) {
         HttpHeaders headers = new HttpHeaders();
         headers.add("Authorization", "Bearer " + token);

         HttpEntity<Headers> request = new HttpEntity<Headers>(headers);

         ResponseEntity<CategoriaClientModel> estados = restTemplate
                 .exchange(url, HttpMethod.GET, request, CategoriaClientModel.class);

         return estados.getBody();
     }
     
     public CategoriaClientModel buscar(Long categoriaId) {
         try {
     	String token = obterToken(restTemplate, baseURL + oauthPath);
         return buscarCategoria(restTemplate, baseURL + estadosPath + categoriaId, token);
     			
     
         } catch (HttpClientErrorException e) {
             throw new ClientApiException(e.getMessage(), e);
         }
     }
    
        }
    
    