package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.v1.model.CidadeDTO;
import com.algaworks.algafood.api.v1.model.input.CidadeInputDTO;
import com.algaworks.algafood.apiexterno.problem.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags="Cidades")
public interface CidadeControllerOpenApi {

	@ApiOperation("Lista as cidades")
	public CollectionModel<CidadeDTO> listar();
		
	@ApiOperation("Busca a cidade por Id")
	@ApiResponses({
		@ApiResponse(code = 400, message= "ID da cidade invalida", response = Problem.class),
		@ApiResponse(code = 404, message= "Cidade não encontrada", response = Problem.class)
		})
	public CidadeDTO buscarPorId(@ApiParam(value="ID de uma cidade" , example = "1") Long id);
	
	@ApiOperation("Cadastra uma nova cidade")
	@ApiResponses({
		@ApiResponse(code = 201, message= "Cidade cadastrada"),
		})
	public CidadeDTO adicionar(CidadeInputDTO cidadeInputDTO);
	
	@ApiOperation("Remove uma cidade por Id")
	public void eliminiar(Long id);
	
	@ApiOperation("Atualize a cidade por Id")
	public CidadeDTO atualizar (CidadeInputDTO cidadeInputDTO, Long id);

}
