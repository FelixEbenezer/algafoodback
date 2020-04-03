package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenericAssembler<T, V> {

	@Autowired
	private ModelMapper modelMapper; 
	
	public V toDTO (T t, Class<V> type) {
		return modelMapper.map(t, type);
	}
	
	public List<V> toCollectionDTO(List<T> ts, Class<V> type) {
		return ts.stream().map(t -> toDTO(t, type))
				.collect(Collectors.toList());
	}
}
