package com.algaworks.algafood.api.controller;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.PedidoDtoAssembler;
import com.algaworks.algafood.api.assembler.PedidoDtoDisassembler;
import com.algaworks.algafood.api.assembler.PedidoResumoDtoAssembler;
import com.algaworks.algafood.api.model.PedidoDTO;
import com.algaworks.algafood.api.model.PedidoResumoDTO;
import com.algaworks.algafood.api.model.input.PedidoInputDTO;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradaException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradaException;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.PedidoService;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpecs;
import com.google.common.collect.ImmutableMap;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

	@Autowired
	private PedidoDtoAssembler assemblerPedido;
	
	@Autowired
	private PedidoResumoDtoAssembler assemblerPedidoResumo; 
	
	@Autowired
	private PedidoDtoDisassembler disassemblerPedido; 
	
	@Autowired
	private PedidoService pedidoService; 
	
	@Autowired
	private PedidoRepository pedidoRepository; 
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository; 

	/*	@GetMapping
	public List<PedidoResumoDTO> consular() {
		return assemblerPedidoResumo.toCollectionObject(pedidoService.listar());
	}*/

	// com paginacao
	@GetMapping
	public Page<PedidoResumoDTO> consular(PedidoFilter filtro,@PageableDefault(size = 3, sort = "dataCriacao") Pageable pageable) {
		
		pageable = traduzirPageable(pageable);
		
		Page<Pedido> pedidos = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro),pageable);
		
		if(pedidos.getSize() == 0) {
			usuarioRepository.findById(filtro.getClienteId())
					.orElseThrow(()-> new UsuarioNaoEncontradaException
					(String.format("Id %d do Usuario inexistente", filtro.getClienteId())));
					
			restauranteRepository.findById(filtro.getRestauranteId())
					.orElseThrow(()-> new RestauranteNaoEncontradaException
					(String.format("Restaurante do Id %d inexistente", filtro.getRestauranteId())));

		}
		
		List<PedidoResumoDTO> pedidosResumoDTO = assemblerPedidoResumo.toCollectionObject(pedidos.getContent());
		
		Page<PedidoResumoDTO> pedidosPage = new PageImpl<>(pedidosResumoDTO, 
				                            pageable, pedidos.getTotalElements());
		
		return pedidosPage; 
	}

	//com Total Geral de todos valorTotal
	@GetMapping("total-geral")
	public Map<String, Object> pesquisar(PedidoFilter filtro, 
			@PageableDefault(size = 10) Pageable pageable) {
		pageable = traduzirPageable(pageable);
		
		Page<Pedido> pedidosPage = pedidoRepository.findAll(
				PedidoSpecs.usandoFiltro(filtro), pageable);
		
		List<PedidoResumoDTO> pedidosResumoModel = assemblerPedidoResumo
				.toCollectionObject(pedidosPage.getContent());
				
		Map<String, Object> json = new LinkedHashMap<>();
		json.put("content", pedidosResumoModel);
		json.put("size", pedidosPage.getSize());
		json.put("numberOfElements", pedidosPage.getTotalElements());
		json.put("totalGeral", pedidosResumoModel.stream().map(item-> item.getValorTotal()).reduce(BigDecimal.ZERO,BigDecimal::add));
			
		return json;
	}

	
/*	@GetMapping
	public List<PedidoResumoDTO> consular(PedidoFilter filtro) {
		
		List<Pedido> pedidos = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro));
		
		if(pedidos.size() == 0) {
			usuarioRepository.findById(filtro.getClienteId())
					.orElseThrow(()-> new UsuarioNaoEncontradaException
					(String.format("Id %d do Usuario inexistente", filtro.getClienteId())));
					
			restauranteRepository.findById(filtro.getRestauranteId())
					.orElseThrow(()-> new RestauranteNaoEncontradaException
					(String.format("Restaurante do Id %d inexistente", filtro.getRestauranteId())));

		}
		
	//	if(pedidos.size() == 0) {
		//	usuarioRepository.findById(filtro.getClienteId()).orElseThrow(() -> new NegocioException("Cliente Inexistente!"));
		//	restauranteRepository.findById(filtro.getRestauranteId()).orElseThrow(() -> new NegocioException("Restaurante Inexistente!"));						
		  }
		
		List<PedidoResumoDTO> pedidosResumoDTO = assemblerPedidoResumo.toCollectionObject(pedidos);
		
		return pedidosResumoDTO; 
	}*/
	
/*	@GetMapping
	public List<PedidoResumoDTO> consular(PedidoFilter filtro) {
		List<Pedido> pedidos = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro));
		return assemblerPedidoResumo.toCollectionObject(pedidos);
	}*/


	/*
	@GetMapping
	public MappingJacksonValue consultar(@RequestParam(required = false) String campos) {
		List<Pedido> pedidos = pedidoService.listar();
		List<PedidoResumoDTO> pedidosDTO = assemblerPedidoResumo.toCollectionObject(pedidos);
		
		MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosDTO);
		
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
		
		if(StringUtils.isNotBlank(campos)) {
			filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
			
		}
		pedidosWrapper.setFilters(filterProvider);
		
		return pedidosWrapper;
	}
	
	*/
	
	@GetMapping("/{codigo}")
	public PedidoDTO buscarPor (@PathVariable String codigo) {
		return assemblerPedido.toDTO(pedidoService.buscarPorId(codigo));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDTO adicionar (@RequestBody @Valid PedidoInputDTO pedidoInputDTO) {
		try {
			Pedido pedido = disassemblerPedido.toDomainObject(pedidoInputDTO);
			
			pedido.setCliente(new Usuario());
	        pedido.getCliente().setId(1L);
	        
	        
	        
			return assemblerPedido.toDTO(pedidoService.emitir(pedido));
		}
		catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	private Pageable traduzirPageable(Pageable apiPageable) {
		var mapeamento = ImmutableMap.of(
				"codigo", "codigo",
				"restaurante.nome", "restaurante.nome",
				"nomeCliente", "cliente.nome",
				"valorTotal", "valorTotal"
			);
		
		return PageableTranslator.translate(apiPageable, mapeamento);
	}
}
