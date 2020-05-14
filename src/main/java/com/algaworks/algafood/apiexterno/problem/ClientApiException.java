package com.algaworks.algafood.apiexterno.problem;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
//import org.apache.logging.log4j.*;
import java.util.logging.Logger;

import org.springframework.web.client.RestClientResponseException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


public class ClientApiException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger("");

	
	private Problem problem;
	
	
	
	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public ClientApiException(String message, RestClientResponseException cause) {
		super(message, cause);
		
		deserializeProblem(cause);
	}
	
	private void deserializeProblem(RestClientResponseException cause) {
		
		 ObjectMapper objectMapper = new ObjectMapper();
		 objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);		
		 objectMapper.registerModule(new JavaTimeModule());
		 SimpleModule simpleModule = new SimpleModule();
		 simpleModule.addSerializer(OffsetDateTime.class, new JsonSerializer<OffsetDateTime>() {
		 @Override
		     public void serialize(OffsetDateTime offsetDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) 
		    		 throws IOException, JsonProcessingException {
			                jsonGenerator.writeString(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(offsetDateTime));
			            }
		        });
		    objectMapper.registerModule(simpleModule);

		try {
			this.problem = objectMapper.readValue(cause.getResponseBodyAsString(), Problem.class);
		} catch (JsonProcessingException e) {
//			log.warn("Não foi possível desserializar a resposta em um problema", e);
			log.warning("Não foi possível desserializar a resposta em um problema " + e);
		}
		}
		
}