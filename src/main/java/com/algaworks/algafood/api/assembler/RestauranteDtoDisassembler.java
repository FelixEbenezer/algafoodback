package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.RestauranteInputDTO;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteDtoDisassembler {
	
	@Autowired
	private ModelMapper modelMapper; 
	
	public Restaurante toDomainObject(RestauranteInputDTO restauranteInputDTO) {
		
	
	    return modelMapper.map(restauranteInputDTO, Restaurante.class);
		/*Restaurante res = new Restaurante();
		res.setNome(restauranteInputDTO.getNome());
		res.setTaxaFrete(restauranteInputDTO.getTaxaFrete());
		
		Cozinha coz = new Cozinha();
		coz.setId(restauranteInputDTO.getCozinha().getId());
		
		res.setCozinha(coz);
				
		return res;*/ 
	}
	
	public void copyToDomainObject(RestauranteInputDTO restauranteInputDTO, Restaurante restaurante) {
		restaurante.setCozinha(new Cozinha());
		
		if(restaurante.getEndereco() != null) {
		restaurante.getEndereco().setCidade(new Cidade());
		}
		
		modelMapper.map(restauranteInputDTO, restaurante);
	}

}
