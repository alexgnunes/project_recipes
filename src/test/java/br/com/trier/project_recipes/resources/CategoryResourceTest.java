package br.com.trier.project_recipes.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.project_recipes.ProjectRecipesApplication;
import br.com.trier.project_recipes.config.jwt.LoginDTO;
import br.com.trier.project_recipes.models.Category;

@ActiveProfiles("test")
@SpringBootTest(classes = ProjectRecipesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryResourceTest {

	@Autowired
	protected TestRestTemplate rest;
	
	private HttpHeaders getHeaders(String email, String password){
		LoginDTO loginDTO = new LoginDTO(email, password);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange(
				"/auth/token", 
				HttpMethod.POST,  
				requestEntity,    
				String.class   
				);
		String token = responseEntity.getBody();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		return headers;
	}
	
		private ResponseEntity<Category> getCategory(String url) {
		return rest.exchange(
				url,  
				HttpMethod.GET, 
				new HttpEntity<>(getHeaders("john.doe@example.com", "john")), 
				Category.class
				);
	}

	private ResponseEntity<List<Category>> getCategories(String url) {
		return rest.exchange(
				url, HttpMethod.GET, 
				new HttpEntity<>(getHeaders("john.doe@example.com", "john")), 
				new ParameterizedTypeReference<List<Category>>() {}
			);
	}
	
	@Test
	@DisplayName("Obter Token")
	@Sql({"classpath:/resources/sqls/limpa_tabela.sql"})
	@Sql({"classpath:/resources/sqls/import.sql"})
	public void getToken() {
		LoginDTO loginDTO = new LoginDTO("john.doe@example.com", "john");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange(
				"/auth/token", 
				HttpMethod.POST,  
				requestEntity,    
				String.class   
				);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		String token = responseEntity.getBody();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		ResponseEntity<List<Category>> response =  rest.exchange("/categories", HttpMethod.GET, new HttpEntity<>(null, headers),
				new ParameterizedTypeReference<List<Category>>() {});
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Buscar por id")
	@Sql({"classpath:/resources/sqls/limpa_tabela.sql"})
	@Sql({"classpath:/resources/sqls/import.sql"})
	public void findByIdTest() {
		ResponseEntity<Category> response = getCategory("/categories/2");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		Category commentary = response.getBody();
		assertEquals("Bolos", commentary.getName());
	}

	@Test
	@DisplayName("Buscar por id inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabela.sql"})
	@Sql({"classpath:/resources/sqls/import.sql"})
	public void testGetNotFound() {
		ResponseEntity<Category> response = getCategory("/categories/100");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Buscar por nome")
	@Sql({"classpath:/resources/sqls/limpa_tabela.sql"})
	@Sql({"classpath:/resources/sqls/import.sql"})
	public void findByNameTest() {
		ResponseEntity<List<Category>> response = getCategories("/categories/name/Bolos");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(1, response.getBody().size());
	}
	
	@Test
	@DisplayName("Buscar por nome contendo")
	@Sql({"classpath:/resources/sqls/limpa_tabela.sql"})
	@Sql({"classpath:/resources/sqls/import.sql"})
	public void findByNameContainingTest() {
		ResponseEntity<List<Category>> response = getCategories("/categories/partName/a");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(4, response.getBody().size());
	} 
	
	@Test
	@DisplayName("Listar Todos")
	@Sql({"classpath:/resources/sqls/limpa_tabela.sql"})
	@Sql({"classpath:/resources/sqls/import.sql"})
	public void findAll() {
		ResponseEntity<List<Category>> response =  rest.exchange(
				"/categories", 
				HttpMethod.GET, 
				new HttpEntity<>(getHeaders("john.doe@example.com", "john")),
				new ParameterizedTypeReference<List<Category>>() {} 
				);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(5, response.getBody().size());
	}	
	
	@Test
	@DisplayName("Cadastrar categoria")
	@Sql({"classpath:/resources/sqls/limpa_tabela.sql"})
	@Sql({"classpath:/resources/sqls/import.sql"})
	public void testCreateCategory() {
		Category dto = new Category(null, "insert");
		HttpHeaders headers = getHeaders("john.doe@example.com", "john");
		HttpEntity<Category> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<Category> responseEntity = rest.exchange(
	            "/categories", 
	            HttpMethod.POST,  
	            requestEntity,    
	            Category.class   
	    );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		Category commentary = responseEntity.getBody();
		assertEquals("insert", commentary.getName());
	}
	
	@Test
	@DisplayName("Alterar categoria")
	@Sql({"classpath:/resources/sqls/limpa_tabela.sql"})
	@Sql({"classpath:/resources/sqls/import.sql"})
	public void testUpdateCategory() {
		Category dto = new Category(2, "update");
		HttpHeaders headers = getHeaders("john.doe@example.com", "john");
		HttpEntity<Category> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<Category> responseEntity = rest.exchange(
				"/categories/2", 
				HttpMethod.PUT,  
				requestEntity,    
				Category.class   
				);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		Category commentary = responseEntity.getBody();
		assertEquals("update", commentary.getName());
	}

	@Test
	@DisplayName("Excluir categoria")
	@Sql({"classpath:/resources/sqls/limpa_tabela.sql"})
	@Sql({"classpath:/resources/sqls/import.sql"})
	public void testDeleteCategory() {
		HttpHeaders headers = getHeaders("john.doe@example.com", "john");
		HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<Void> responseEntity = rest.exchange(
				"/categories/6", 
				HttpMethod.DELETE,  
				requestEntity, 
				Void.class
				);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Excluir categoria inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabela.sql"})
	@Sql({"classpath:/resources/sqls/import.sql"})
	public void testDeleteNonExistCategory() {
		HttpHeaders headers = getHeaders("john.doe@example.com", "john");
		HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<Void> responseEntity = rest.exchange(
				"/categories/100", 
				HttpMethod.DELETE,  
				requestEntity, 
				Void.class
				);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
	}
}
