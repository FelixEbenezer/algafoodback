package com.algaworks.algafood.apiexterno.laposteClient;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.algaworks.algafood.apiexterno.problem.ClientApiException;
import com.sun.net.httpserver.Headers;

@Component
public class ColisClient {
	
	
   // String baseURL = "https://api.laposte.fr/suivi/v2/idships/EP111111110FR?lang=fr_FR";
    String baseURL = "https://api.laposte.fr/suivi/v2/idships/";

    RestTemplate restTemplate = new RestTemplate();
    
   
    public static ColisClientModel adicionarColis(ColisClientInput categoria, RestTemplate restTemplate, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "X-Okapi-Key U4ctYdEgZn4caOfoo0xOlQXqjUaHMEAMKlZLf2StiIg3lmvkIrcIerEt/x0Xg7oS");
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
        headers.add("X-Okapi-Key" , "U4ctYdEgZn4caOfoo0xOlQXqjUaHMEAMKlZLf2StiIg3lmvkIrcIerEt/x0Xg7oS");
        
        HttpEntity<ColisClientInput> request = new HttpEntity<>(categoria, headers);

        ResponseEntity<ColisClientModel> estados = restTemplate
                .exchange(url, HttpMethod.POST, request, ColisClientModel.class);

        return estados.getBody();
    }

     public ColisClientModel adicionar(ColisClientInput categoriaClienteInput) {
        try {
                ColisClientModel estados = adicionarColis(categoriaClienteInput, restTemplate, baseURL);
        			
        	return estados;
        } catch (HttpClientErrorException e) {
            throw new ClientApiException(e.getMessage(), e);
        }
    }
     
     
     
     public static ColisClientModel buscarCategoria(RestTemplate restTemplate, String url) {
         HttpHeaders headers = new HttpHeaders();
         headers.add("Authorization", "X-Okapi-Key U4ctYdEgZn4caOfoo0xOlQXqjUaHMEAMKlZLf2StiIg3lmvkIrcIerEt/x0Xg7oS");
         headers.add("Content-Type", "application/json");
         headers.add("Accept", "application/json");
         headers.add("X-Okapi-Key" , "U4ctYdEgZn4caOfoo0xOlQXqjUaHMEAMKlZLf2StiIg3lmvkIrcIerEt/x0Xg7oS");

         HttpEntity<Headers> request = new HttpEntity<Headers>(headers);

         ResponseEntity<ColisClientModel> estados = restTemplate
                 .exchange(url, HttpMethod.GET, request, ColisClientModel.class);

         return estados.getBody();
     }
    
     public ColisClientModel buscar(String id) {
         try {
     	
         return buscarCategoria(restTemplate, baseURL + id);
     			
     
         } catch (HttpClientErrorException e) {
             throw new ClientApiException(e.getMessage(), e);
         }
     }
     
     public ColisClientModel buscar2(ColisClientInput cc) {
         try {
     	
         return buscarCategoria(restTemplate, baseURL + cc);
     			
     
         } catch (HttpClientErrorException e) {
             throw new ClientApiException(e.getMessage(), e);
         }
     }
    
        }
    
    