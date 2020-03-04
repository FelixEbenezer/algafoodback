package com.algaworks.algafood;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import javax.validation.ConstraintViolationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.RestauranteService;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteIT {
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private RestauranteRepository restauranteRepository; 
	
	@Autowired
	private CozinhaRepository cozinhaRepository; 
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner; 
	
	private String jsonCorretoRestaurante;
	private String jsonIncorretoRestaurante; 
	
	//setUp
	
	@Before
	public void setUp () {
	
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port; 
		RestAssured.basePath = "/restaurantes"; 
		
		jsonCorretoRestaurante = ResourceUtils.getContentFromResource(
				"/json/correto/restaurante-teste.json");
		
		jsonIncorretoRestaurante = ResourceUtils.getContentFromResource(
				"/json/incorreto/restauranteIncorreto-teste.json");
		
		databaseCleaner.clearTables();
		preparaDados();
		
	}
	
	private void preparaDados() {
		
		
		Cozinha cos1 = new Cozinha();
		cos1.setNome("Brasileira");
		cozinhaRepository.save(cos1);
		
		Cozinha cos2 = new Cozinha();
		cos2.setNome("Argentina");
		cozinhaRepository.save(cos2);
		
		Restaurante res1 = new Restaurante();
		res1.setNome("Cuia Bue");
		res1.setTaxaFrete(new BigDecimal(100));
		res1.setCozinha(cos1);
		restauranteRepository.save(res1);
		
		Restaurante res2 = new Restaurante();
		res2.setNome("Chilling");
		res2.setTaxaFrete(new BigDecimal(250));
		res2.setCozinha(cos2);
		restauranteRepository.save(res2);
		
		
	}

//TESTEs API trabalhem com metodos repository : restauranteRepository junto com Controller
	
	//1. caminho feliz para cadastrar um restaurante 
	
	@Test
	public void deveRetornar201_quandoCadastrarRestauranteSucesso() {
		RestAssured.given()
					.body(jsonCorretoRestaurante)
					.contentType(ContentType.JSON)
					.accept(ContentType.JSON)
				.when()
					.post()
				.then()
					.statusCode(HttpStatus.CREATED.value());
	}
	
	//1.1. Um dos caminhos infeliz do cadastro do restaurante 
	
	@Test
	public void deveRetornar400_quandoCadastrarRestauranteNomeNull() {
		RestAssured.given()
					.body(jsonIncorretoRestaurante)
					.contentType(ContentType.JSON)
					.accept(ContentType.JSON)
				.when()
					.post()
				.then()
					.statusCode(HttpStatus.BAD_REQUEST.value());
					// .body("title", Matchers.equalTo("Dados invalidos"));
	}
	
	//2. caminho feliz para consulta de restaurantes 
	
	@Test
	public void deveRetornar200_quandoCOnsultarRestaurante() {
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	//2.1 caminho infeliz para consulta de restaurantes 
	
		@Test
		public void deveRetornar406_quandoCOnsultarRestaurante() {
			RestAssured.given()
				.accept(ContentType.TEXT)
			.when()
				.get()
			.then()
				.statusCode(HttpStatus.NOT_ACCEPTABLE.value());
		}
	
		//3. caminho feliz para consultar restaurante por id, logo ha param
		
		@Test
		public void deveRetornar200_quandoConsultarResId() {
			RestAssured.given()
					.pathParam("id", 1)
					.accept(ContentType.JSON)
			.when()
					.get("/{id}")
			.then()
					.statusCode(HttpStatus.OK.value());
			
		}
		
		//3.1 caminho infeliz para consultar restaurante por id, logo ha param
		
				@Test
				public void deveRetornar404_quandoConsultarResIdInexistente() {
					RestAssured.given()
							.pathParam("id", 1000)
							.accept(ContentType.JSON)
					.when()
							.get("/{id}")
					.then()
							.statusCode(HttpStatus.NOT_FOUND.value());
					
				}
				
// 2. TESTES DE INTEGRAÇÃO trabalhem com metodos de Service: restauranteService
				
		//1. testar cadastro de um restaurante com sucesso
			
			@Test
			public void testarCadastroRestauranteSucesso() {
				
				Cozinha cos2 = new Cozinha();
				cos2.setNome("Chilena");
				cozinhaRepository.save(cos2);
				
				Restaurante res1 = new Restaurante();
				res1.setNome("Cuia Bue1");
				res1.setTaxaFrete(new BigDecimal(100));
				res1.setCozinha(cos2);
				
				res1 = restauranteService.adicionarRestaurante(res1);
				
				
				assertThat(res1.getId()).isNotNull();
			}
			
         //1.1. testar cadastro de um restaurante com Insucesso com nome null
			
			@Test(expected = ConstraintViolationException.class)
			public void testarCadastroRestauranteInsucessoNomeNull() {
				
				Cozinha cos2 = new Cozinha();
				cos2.setNome("Chilena");
				cozinhaRepository.save(cos2);
				
				Restaurante res1 = new Restaurante();
				res1.setNome(null);
				res1.setTaxaFrete(new BigDecimal(100));
				res1.setCozinha(cos2);
				
				res1 = restauranteService.adicionarRestaurante(res1);
				
			}
			
          //1.2. testar cadastro de um restaurante com Insucesso em taxaFreteNegativo
			
			@Test(expected = ConstraintViolationException.class)
			public void testarCadastroRestauranteInsucessoTaxaFreteNegativo() {
				
				Cozinha cos2 = new Cozinha();
				cos2.setNome("Chilena");
				cozinhaRepository.save(cos2);
				
				Restaurante res1 = new Restaurante();
				res1.setNome("Bela shop");
				res1.setTaxaFrete(new BigDecimal(-100));
				res1.setCozinha(cos2);
				
				res1 = restauranteService.adicionarRestaurante(res1);
				
			}
			
       	

}
