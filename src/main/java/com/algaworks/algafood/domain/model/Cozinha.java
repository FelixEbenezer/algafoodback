package com.algaworks.algafood.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algaworks.algafood.core.validation.Groups;

@Entity
@Table
public class Cozinha {

	@NotNull(groups = Groups.CadastroRestaurante.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@NotBlank
	private String nome;
	
	// @JsonIgnore   // para não sair em representação para nao entrar em referencia circular
	@OneToMany(mappedBy = "cozinha")  // cozinha é o nome dado no campo da tablea restaurante para fazer o mapeamente de 2 tables
	private List<Restaurante> restaurantes = new ArrayList<>();

	
	public List<Restaurante> getRestaurantes() {
		return restaurantes;
	}

	public void setRestaurantes(List<Restaurante> restaurantes) {
		this.restaurantes = restaurantes;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cozinha other = (Cozinha) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	} 
	
	
}
