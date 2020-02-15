package com.algaworks.algafood;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CozinhaService;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT {
	
	@Autowired
	private CozinhaService cozinhaService;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner; 
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	// para refatoramento
	
	private Cozinha cozinhaAmericana;
	private int quantidadeCozinhasCadastradas;
	private String jsonCorretoCozinhaChinesa;
	
	private static final int COZINHA_ID_INEXISTENTE = 100;
	
// setup metodo call back

	@Before
	public void setUp () {
	
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port; 
		RestAssured.basePath = "/cozinhas"; 
		
		jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource(
				"/json/correto/cozinha-teste.json");
		
		// flyway.migrate(); 
		databaseCleaner.clearTables();
		preparaDados();
		
	}
//2. TESTE DE API PARA COZINHA=============================================================
	
	@Test
	public void deveRetornarStatusCorreto_quandoConsultarCozinha() {
		
		RestAssured.given()
			.pathParam("id", cozinhaAmericana.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{id}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", Matchers.equalTo(cozinhaAmericana.getNome()));
	}
	
	@Test
	public void deveRetornarStatus404_quandoConsultarCozinhaInexistente() {
		
		RestAssured.given()
			.pathParam("id", COZINHA_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{id}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
			//.body("nome", Matchers.equalTo(cozinhaAmericana.getNome()));
	}
	
	@Test
	public void testRetornarStatus201_QuandoCadastrarCozinha() {
		RestAssured.given()
			// .body("{ \"nome\": \"Chinesa\" }")
			.body(jsonCorretoCozinhaChinesa)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		RestAssured.given()
			.accept(ContentType.JSON)
			.when()
				.get()
			.then()
				.statusCode(HttpStatus.OK.value());
			
	}
	
	@Test
	public void deveConter2Cozinhas_QuandoConsultarCozinhas() {
		
		RestAssured.given()
			.accept(ContentType.JSON)
			.when()
				.get()
			.then()
				.body("", Matchers.hasSize(quantidadeCozinhasCadastradas))
				.body("nome", Matchers.hasItems("Americana", "Tailandesa"));
			
	}
	
	private void preparaDados() {
		
		Cozinha cozinhaTailandesa = new Cozinha();
		cozinhaTailandesa.setNome("Tailandesa");
		cozinhaRepository.save(cozinhaTailandesa);

		cozinhaAmericana = new Cozinha();
		cozinhaAmericana.setNome("Americana");
		cozinhaRepository.save(cozinhaAmericana);
		
		quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();
		
		/*Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Senegalesa");
		cozinhaRepository.save(cozinha1); 
		
		Cozinha cozinha2 = new Cozinha();
		cozinha1.setNome("ivoirense");
		cozinhaRepository.save(cozinha2); */
		
	}
	
	/*
	@Test
	public void deveConter4Restaurantes_QuandoConsultarRestaurantes() {
		
		RestAssured.given()
			.basePath("/restaurantes")
			.port(port)
			.accept(ContentType.JSON)
			.when()
				.get()
			.then()
				.body("", Matchers.hasSize(3))
				.body("nome", Matchers.hasItems("Thai Gourmet"))
				.body("taxaFrete", Matchers.greaterThan(5));
			
	}*/

	
	//1. TESTE DE INTEGRAÇÃO ==========================================================
		@Test
		public void testarCozinhaComSucesso() {
			
			// cenario
			
			Cozinha novaCozinha = new Cozinha();
			novaCozinha.setNome("Malina");
			
			// ação : chamar o metodo adicionar do CozinhaService
			novaCozinha = cozinhaService.salvar(novaCozinha);
			
			//validação 
			assertThat(novaCozinha).isNotNull();
			assertThat(novaCozinha.getId()).isNotNull();
		}
		
		@Test(expected = ConstraintViolationException.class)
		public void testarCozinhaComFalha1_SemNome() {
			Cozinha cozinha1 = new Cozinha();
			cozinha1.setNome(null);
			
			cozinha1 = cozinhaService.salvar(cozinha1);
		}
		
		@Test
		public void testarExclusaoCozinhaComSucesso() {
			
			cozinhaService.remover(2L);
		}
		
		@Test(expected = EntidadeEmUsoException.class)
		public void testarExclusaoCozinhaFalha1_IdEmUso() {
			
			cozinhaService.remover(1L);
		}
		
		@Test(expected = EntidadeNaoEncontradaException.class)
		public void testarExclusaoFalha2_IdInexistente() {
			cozinhaService.remover(100L);
		}

}
