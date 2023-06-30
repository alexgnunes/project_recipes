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
import br.com.trier.project_recipes.models.dto.CommentaryDTO;

@ActiveProfiles("test")
@SpringBootTest(classes = ProjectRecipesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CommentaryResourceTest {

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
	
		private ResponseEntity<CommentaryDTO> getCommentary(String url) {
		return rest.exchange(
				url,  
				HttpMethod.GET, 
				new HttpEntity<>(getHeaders("john.doe@example.com", "john")), 
				CommentaryDTO.class
				);
	}

	private ResponseEntity<List<CommentaryDTO>> getCommentaries(String url) {
		return rest.exchange(
				url, HttpMethod.GET, 
				new HttpEntity<>(getHeaders("john.doe@example.com", "john")), 
				new ParameterizedTypeReference<List<CommentaryDTO>>() {}
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
		ResponseEntity<List<CommentaryDTO>> response =  rest.exchange("/commentaries", HttpMethod.GET, new HttpEntity<>(null, headers),
				new ParameterizedTypeReference<List<CommentaryDTO>>() {});
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
//	@Test
//	@DisplayName("Buscar por id")
//	@Sql({"classpath:/resources/sqls/limpa_tabela.sql"})
//	@Sql({"classpath:/resources/sqls/import.sql"})
//	public void findByIdTest() {
//		ResponseEntity<CommentaryDTO> response = getCommentary("/commentaries/2");
//		assertEquals(response.getStatusCode(), HttpStatus.OK);
//		CommentaryDTO commentary = response.getBody();
//		assertEquals("Ótima receita!", commentary.getContent());
//	}
//
//	@Test
//	@DisplayName("Buscar por id inexistente")
//	@Sql({"classpath:/resources/sqls/limpa_tabela.sql"})
//	@Sql({"classpath:/resources/sqls/import.sql"})
//	public void testGetNotFound() {
//		ResponseEntity<CommentaryDTO> response = getCommentary("/commentaries/100");
//		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
//	}
//	
//	@Test
//	@DisplayName("Buscar por nome")
//	@Sql({"classpath:/resources/sqls/limpa_tabela.sql"})
//	@Sql({"classpath:/resources/sqls/import.sql"})
//	public void findByContentContainingTest() {
//		ResponseEntity<List<CommentaryDTO>> response = getCommentaries("/commentaries/content/receita");
//		assertEquals(response.getStatusCode(), HttpStatus.OK);
//		assertEquals(1, response.getBody().size());
//	}
//	@Test
//	@DisplayName("Listar Todos")
//	@Sql({"classpath:/resources/sqls/limpa_tabela.sql"})
//	@Sql({"classpath:/resources/sqls/import.sql"})
//	public void findAll() {
//		ResponseEntity<List<CommentaryDTO>> response =  rest.exchange(
//				"/commentaries", 
//				HttpMethod.GET, 
//				new HttpEntity<>(getHeaders("john.doe@example.com", "john")),
//				new ParameterizedTypeReference<List<CommentaryDTO>>() {} 
//				);
//		assertEquals(response.getStatusCode(), HttpStatus.OK);
//		assertEquals(6, response.getBody().size());
//	}	
//	
	@Test
	@DisplayName("Cadastrar usuário")
	@Sql({"classpath:/resources/sqls/limpa_tabela.sql"})
	@Sql({"classpath:/resources/sqls/import.sql"})
	public void testCreateCommentary() {
		CommentaryDTO dto = new CommentaryDTO(null, "insert", null, null, null, null);
		HttpHeaders headers = getHeaders("john.doe@example.com", "john");
		HttpEntity<CommentaryDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<CommentaryDTO> responseEntity = rest.exchange(
	            "/commentaries", 
	            HttpMethod.POST,  
	            requestEntity,    
	            CommentaryDTO.class   
	    );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		CommentaryDTO commentary = responseEntity.getBody();
		assertEquals("insert", commentary.getContent());
	}
//	
//	@Test
//	@DisplayName("Alterar usuário")
//	@Sql({"classpath:/resources/sqls/limpa_tabela.sql"})
//	@Sql({"classpath:/resources/sqls/import.sql"})
//	public void testUpdateCommentary() {
//		CommentaryDTO dto = new CommentaryDTO(2, "update", null, null, null, null);
//		HttpHeaders headers = getHeaders("john.doe@example.com", "john");
//		HttpEntity<CommentaryDTO> requestEntity = new HttpEntity<>(dto, headers);
//		ResponseEntity<CommentaryDTO> responseEntity = rest.exchange(
//				"/commentaries/2", 
//				HttpMethod.PUT,  
//				requestEntity,    
//				CommentaryDTO.class   
//				);
//		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
//		CommentaryDTO commentary = responseEntity.getBody();
//		assertEquals("update", commentary.getContent());
//	}
//
//	@Test
//	@DisplayName("Excluir usuário")
//	@Sql({"classpath:/resources/sqls/limpa_tabela.sql"})
//	@Sql({"classpath:/resources/sqls/import.sql"})
//	public void testDeleteCommentary() {
//		HttpHeaders headers = getHeaders("john.doe@example.com", "john");
//		HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
//		ResponseEntity<Void> responseEntity = rest.exchange(
//				"/commentaries/3", 
//				HttpMethod.DELETE,  
//				requestEntity, 
//				Void.class
//				);
//		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
//	}
//	
//	@Test
//	@DisplayName("Excluir usuário inexistente")
//	@Sql({"classpath:/resources/sqls/limpa_tabela.sql"})
//	@Sql({"classpath:/resources/sqls/import.sql"})
//	public void testDeleteNonExistCommentary() {
//		HttpHeaders headers = getHeaders("john.doe@example.com", "john");
//		HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
//		ResponseEntity<Void> responseEntity = rest.exchange(
//				"/commentaries/100", 
//				HttpMethod.DELETE,  
//				requestEntity, 
//				Void.class
//				);
//		assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
//	}
}
