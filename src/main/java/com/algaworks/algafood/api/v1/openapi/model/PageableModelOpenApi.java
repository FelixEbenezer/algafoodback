package com.algaworks.algafood.api.v1.openapi.model;

import java.util.List;

public class PageableModelOpenApi {
	
	private int page;
	private int size;
	private List<String> sort;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public List<String> getSort() {
		return sort;
	}
	public void setSort(List<String> sort) {
		this.sort = sort;
	} 
	
	

}
