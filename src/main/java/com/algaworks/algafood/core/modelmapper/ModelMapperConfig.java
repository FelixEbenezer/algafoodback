package com.algaworks.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.api.v1.model.FormaPagamentoDTO;
import com.algaworks.algafood.api.v1.model.PedidoDTO;
import com.algaworks.algafood.api.v1.model.RestauranteDTO;
import com.algaworks.algafood.api.v1.model.input.ItemInputDTO;
import com.algaworks.algafood.api.v2.model.input.CidadeInputDTOV2;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.ItemPedido;
import com.algaworks.algafood.domain.model.Pedido;
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
		
		modelMapper.createTypeMap(Pedido.class, PedidoDTO.class)
		.addMapping(Pedido::getEndereco, PedidoDTO::setEnderecoEntrega);
		
		

       modelMapper.createTypeMap(CidadeInputDTOV2.class, Cidade.class).addMappings(mapper->mapper.skip(Cidade::setId));
		
		
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
