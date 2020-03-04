package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.FormaPagamentoDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoAssembler {

	@Autowired
	private ModelMapper modelMapper; 
	
	public FormaPagamentoDTO toDTO (FormaPagamento formaPagamento) {
		return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
	}
	
	public List<FormaPagamentoDTO> toCollectionObject(List<FormaPagamento> formasPagamento) {
		return formasPagamento.stream().map(formaPagamento -> toDTO(formaPagamento))
				.collect(Collectors.toList());
	}
}
