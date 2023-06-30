package br.com.trier.project_recipes.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
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
import br.com.trier.project_recipes.models.dto.RecipeByDifficultyAndCategoryDTO;

@ActiveProfiles("test")
@SpringBootTest(classes = ProjectRecipesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReportResourceTest {

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
	
	private ResponseEntity<RecipeByDifficultyAndCategoryDTO> getSearch(String url) {
		return rest.exchange(
				url,  
				HttpMethod.GET, 
				new HttpEntity<>(getHeaders("john.doe@example.com", "john")), 
				RecipeByDifficultyAndCategoryDTO.class
				);
	}
	
	@Test
	@DisplayName("Busca receita por categoria e dificuldade")
	@Sql({"classpath:/resources/sqls/limpa_tabela.sql"})
	@Sql({"classpath:/resources/sqls/import.sql"})
	public void reportTest() {
		ResponseEntity<RecipeByDifficultyAndCategoryDTO> response = getSearch("/reports/recipe-difficult-category/DIFICIL/2");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		RecipeByDifficultyAndCategoryDTO result = response.getBody();
		assertEquals("Bolos", result.getCategory());
	}
	
	
}
