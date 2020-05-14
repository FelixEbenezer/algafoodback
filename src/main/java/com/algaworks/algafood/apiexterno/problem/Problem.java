package com.algaworks.algafood.apiexterno.problem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;

@ApiModel("Problema")
public class Problem {

	private Integer status;
//	private OffsetDateTime timestamp;
	private LocalDateTime timestamp;
	private String userMessage;
	
	
	private List<Object> objects = new ArrayList<>();
	
	@ApiModel("ObjetoProblema")
	public static class Object {
		  
		  private String name;
		  private String userMessage;
		   
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getUserMessage() {
			return userMessage;
		}
		public void setUserMessage(String userMessage) {
			this.userMessage = userMessage;
		}
		  }
	
	public List<Object> getObjects() {
		return objects;
	}
	public void setObjects(List<Object> objects) {
		this.objects = objects;
	}
	
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	public String getUserMessage() {
		return userMessage;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}
	
	
	
}