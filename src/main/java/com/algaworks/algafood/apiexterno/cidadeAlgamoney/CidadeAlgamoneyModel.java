package com.algaworks.algafood.apiexterno.cidadeAlgamoney;

public class CidadeAlgamoneyModel {

	private Long codigo;
	private String nome;
	private EstadoAlgamoneyModel2 estado;
	
	
	
	public EstadoAlgamoneyModel2 getEstado() {
		return estado;
	}
	public void setEstado(EstadoAlgamoneyModel2 estado) {
		this.estado = estado;
	}
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
}