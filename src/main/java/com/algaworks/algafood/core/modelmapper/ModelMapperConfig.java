package com.algaworks.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.api.model.FormaPagamentoDTO;
import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.api.model.input.ItemInputDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.ItemPedido;
import com.algaworks.algafood.domain.model.Restaurante;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
		modelMapper.createTypeMap(Restaurante.class, RestauranteDTO.class)
        .addMapping(Restaurante::getTaxaFrete, RestauranteDTO::setPrecoFrete)
        .addMapping(restSrc -> restSrc.getEndereco().getCidade().getEstado().getNome(), 
        	(restDest, val) -> restDest.getEndereco().getCidade().setNomeEstado((String) val));
		
		modelMapper.createTypeMap(FormaPagamento.class, FormaPagamentoDTO.class)
		.addMapping(FormaPagamento::getDescricao, FormaPagamentoDTO::setDescricaoForma);
		
		//modelMapper.createTypeMap(Usuario.class, UsuarioSenhaInputDTO.class)
		//.addMapping(Usuario::getSenha, UsuarioSenhaInputDTO::setSenhaAtual);
		
		modelMapper.createTypeMap(ItemInputDTO.class, ItemPedido.class)
	    .addMappings(mapper -> mapper.skip(ItemPedido::setId)); 
		
		return modelMapper;
	}
	
/*	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}*/
	
//	modelMapper.createTypeMap(Restaurante.class, RestauranteDTO.class)
		//	.addMapping(Restaurante::getTaxaFrete, RestauranteDTO::setPrecoFrete);
		
	/*	var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(
				Endereco.class, EnderecoDTO.class);
		
		enderecoToEnderecoModelTypeMap.<String>addMapping(
				enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
				(enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado((String)value));
		
		
		.addMapping(restSrc -> restSrc.getFormasPagamento().getDescricao(), 
	        	(restDest, val) -> restDest.getFormasPagamento().setDescricao( val))
		*
		*/

}
