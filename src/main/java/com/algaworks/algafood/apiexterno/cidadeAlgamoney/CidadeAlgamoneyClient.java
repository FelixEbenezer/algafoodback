package com.algaworks.algafood.apiexterno.cidadeAlgamoney;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.algaworks.algafood.apiexterno.problem.ClientApiException;
import com.sun.net.httpserver.Headers;

@Component
public class CidadeAlgamoneyClient {
	
	
    String baseURL = "https://ngangalixapi.herokuapp.com";
    String oauthPath = "/oauth/token";
    String estadosPath = "/cidades";

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
    public static CidadeAlgamoneyModel[] obterEstados(RestTemplate restTemplate, String url, String token, Long estado) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        HttpEntity<Headers> request = new HttpEntity<Headers>(headers);
        
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
        		.fromHttpUrl(url)
        		.queryParam("estado", estado);

        String urlComParametros = uriBuilder.toUriString(); 

        ResponseEntity<CidadeAlgamoneyModel[]> estados = restTemplate
                .exchange(urlComParametros, HttpMethod.GET, request, CidadeAlgamoneyModel[].class);

        return estados.getBody();
    }
     
    public List<CidadeAlgamoneyModel> listar1(Long estado) {
        try {
    	String token = obterToken(restTemplate, baseURL + oauthPath);
        
    	CidadeAlgamoneyModel[] estados = obterEstados(restTemplate, baseURL + estadosPath, token, estado);
    			
    	return Arrays.asList(estados);
        } catch (HttpClientErrorException e) {
            throw new ClientApiException(e.getMessage(), e);
        }
    }
    
    
    
    /*
    public List<CidadeAlgamoneyModel> listar2(Long estado) {
    	try {
    		UriComponents uri = UriComponentsBuilder.fromHttpUrl(url + RESOURCE_PATH)
    				.queryParam("estado", estado)
    				.build();
    			
    		CidadeAlgamoneyModel[] cidades = restTemplate
    				.getForObject(uri.toUri(), CidadeAlgamoneyModel[].class);
    			
    		return Arrays.asList(cidades);
    	} catch (HttpClientErrorException e) {
                    throw new ClientApiException(e.getMessage(), e);
            }
    }*/
	
}

