package com.algaworks.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.CidadeDTO;

import io.swagger.annotations.ApiModel;

@ApiModel("CidadesModel")
public class CidadesModelOpenApi {

		private CidadeEmbeddedModelOpenApi _embedded;
		private Links _links;
		
				public CidadeEmbeddedModelOpenApi get_embedded() {
					return _embedded;
				}
		
				public void set_embedded(CidadeEmbeddedModelOpenApi _embedded) {
					this._embedded = _embedded;
				}
		
				public Links get_links() {
					return _links;
				}
				public void set_links(Links _links) {
					this._links = _links;
				}

		@ApiModel("CidadesEmbeddedModel")
		public class CidadeEmbeddedModelOpenApi {
			
			private List<CidadeDTO> cidadeDTOList;

			public List<CidadeDTO> getCidadeDTOList() {
				return cidadeDTOList;
			}

			public void setCidadeDTOList(List<CidadeDTO> cidadeDTOList) {
				this.cidadeDTOList = cidadeDTOList;
			}

			
						
		}
		
	}
