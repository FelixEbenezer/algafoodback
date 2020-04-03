package com.algaworks.algafood.api.vendaDiariaApi;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.vendaDiariaDomain.VendaDiariaFilter;

@Component
public class VendaDiariaDtoAssembler {

	@Autowired
	private ModelMapper modelMapper; 
	
	public VendaDiariaDTO toDTO (VendaDiariaFilter filtro) {
		return modelMapper.map(filtro, VendaDiariaDTO.class);
	}
	
	public List<VendaDiariaDTO> toCollectionObject(List<VendaDiariaFilter> filtros){
		return filtros.stream()
						.map(filtro -> toDTO(filtro))
						.collect(Collectors.toList());
	}
}
