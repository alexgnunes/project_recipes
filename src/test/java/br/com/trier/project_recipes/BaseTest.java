package br.com.trier.project_recipes;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import br.com.trier.project_recipes.services.CategoryService;
import br.com.trier.project_recipes.services.CommentaryService;
import br.com.trier.project_recipes.services.PersonService;
import br.com.trier.project_recipes.services.RecipeService;
import br.com.trier.project_recipes.services.impl.CategoryServiceImpl;
import br.com.trier.project_recipes.services.impl.CommentaryServiceImpl;
import br.com.trier.project_recipes.services.impl.PersonServiceImpl;
import br.com.trier.project_recipes.services.impl.RecipeServiceImpl;

@TestConfiguration
@SpringBootTest
@ActiveProfiles("test")
public class BaseTest {

	@Bean
	public RecipeService recipeService() {
		return new RecipeServiceImpl();
	}
	
	@Bean
	public PersonService personService() {
		return new PersonServiceImpl();
	}
	
	@Bean
	public CommentaryService commentaryService() {
		return new CommentaryServiceImpl();
	}
	
	@Bean
	public CategoryService categoryService() {
		return new CategoryServiceImpl();
	}
}
