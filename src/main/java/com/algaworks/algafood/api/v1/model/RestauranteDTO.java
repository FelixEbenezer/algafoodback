package com.algaworks.algafood.api.v1.model;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "restaurantes")
public class RestauranteDTO extends RepresentationModel<RestauranteDTO> {
	
//	@JsonView(RestauranteView.Resumo.class)
	private Long id;
	
//	@JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
	private String nome; 
	
//	@JsonView(RestauranteView.Resumo.class)
	private BigDecimal precoFrete;
	
//	@JsonView(RestauranteView.Resumo.class)
	private CozinhaDTO cozinha;
	
	private Boolean ativo;
	
	private Boolean aberto; 
	
	private EnderecoDTO endereco; 
	
	private List<FormaPagamentoDTO> formasPagamento; 
	

	
	
	
	
	
	/*	private List<FormaPagamento> formasPagamento;

	public List<FormaPagamento> getFormasPagamento() {
		return formasPagamento;
	}

	public void setFormasPagamento(List<FormaPagamento> formasPagamento) {
		this.formasPagamento = formasPagamento;
	}
*/
	
	public Boolean getAberto() {
		return aberto;
	}

	public void setAberto(Boolean aberto) {
		this.aberto = aberto;
	}

	
	public List<FormaPagamentoDTO> getFormasPagamento() {
		return formasPagamento;
	}

	public void setFormasPagamento(List<FormaPagamentoDTO> formasPagamento) {
		this.formasPagamento = formasPagamento;
	}

	public Long getId() {
		return id;
	}

	public EnderecoDTO getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoDTO endereco) {
		this.endereco = endereco;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getPrecoFrete() {
		return precoFrete;
	}

	public void setPrecoFrete(BigDecimal precoFrete) {
		this.precoFrete = precoFrete;
	}

	public CozinhaDTO getCozinha() {
		return cozinha;
	}

	public void setCozinha(CozinhaDTO cozinha) {
		this.cozinha = cozinha;
	} 
	
	
	

}
