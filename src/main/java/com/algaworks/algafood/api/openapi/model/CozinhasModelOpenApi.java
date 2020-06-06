package com.algaworks.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.CozinhaDTO;

import io.swagger.annotations.ApiModel;

@ApiModel("CozinhasModel")
public class CozinhasModelOpenApi {
	
	private CozinhaEmbeddedModelOpenApi _embedded;
	private Links _links;
	private PageModelOpenApi page; 
	
	
	
	public CozinhaEmbeddedModelOpenApi get_embedded() {
		return _embedded;
	}



	public void set_embedded(CozinhaEmbeddedModelOpenApi _embedded) {
		this._embedded = _embedded;
	}



	public Links get_links() {
		return _links;
	}



	public void set_links(Links _links) {
		this._links = _links;
	}



	public PageModelOpenApi getPage() {
		return page;
	}



	public void setPage(PageModelOpenApi page) {
		this.page = page;
	}



	@ApiModel("CozinhasEmbeddedModel")
	public class CozinhaEmbeddedModelOpenApi {
		
		private List<CozinhaDTO> cozinhaDTOList;

		public List<CozinhaDTO> getCozinhaDTOList() {
			return cozinhaDTOList;
		}

		public void setCozinhaDTOList(List<CozinhaDTO> cozinhaDTOList) {
			this.cozinhaDTOList = cozinhaDTOList;
		}
		
		
	}
	
}
