package com.algaworks.algafood.praticas;

public class Produto {
	
	private Long id; 
	
	private String nome; 
	
	private int anoFabrico; 
	
	private double preco;

	public Produto(Long id, String nome, int anoFabrico, double preco) {
		this.id = id;
		this.nome = nome;
		this.anoFabrico = anoFabrico;
		this.preco = preco;
	}

	public Long getId() {
		return id;
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

	public int getAnoFabrico() {
		return anoFabrico;
	}

	public void setAnoFabrico(int anoFabrico) {
		this.anoFabrico = anoFabrico;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return id+"/"+nome+"/"+anoFabrico+"/"+preco;
	}
	

}
