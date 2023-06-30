package br.com.trier.project_recipes.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import br.com.trier.project_recipes.BaseTest;
import br.com.trier.project_recipes.models.Favorite;
import br.com.trier.project_recipes.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/import.sql")
class FavoriteServiceTest extends BaseTest{

	@Autowired
	FavoriteService service;
	
	@Autowired
	PersonService personService;
	
	@Autowired
	RecipeService recipeService;
	
	@Test
	@DisplayName("busca por id")
	void findByIdTest() {
		Favorite favorite = service.findById(2);
		assertNotNull(favorite);
		assertEquals(2, favorite.getId()); 
		assertEquals("John Doe", favorite.getPerson().getName()); 
	}
	
	@Test
	@DisplayName("busca por id inexistente")
	void findByIdNomExistIdTest() {
		var ex = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("Id 10 não encontrada", ex.getMessage());
	}

	@Test
	@DisplayName("busca por usuario")
	void findByPersonTest() {
		List<Favorite> list = service.findByPerson(personService.findById(2));
		assertEquals(2, list.size());
		assertEquals("John Doe", list.get(0).getPerson().getName());
	}
	
	@Test
	@DisplayName("busca por usuario inexistente")
	void findByNomExistPersonTest() {
		var ex = assertThrows(ObjectNotFound.class, () -> service
				.findByPerson(personService.findById(1)));
		assertEquals("Usuario(a) 1 não encontrado(a)", ex.getMessage());
	}		
	
	@Test
	@DisplayName("busca por receita")
	void findByRecipeTest() {
		List<Favorite> list = service.findByRecipe(recipeService.findById(2));
		assertEquals(2, list.size());
		assertEquals("Bolo Simples", list.get(0).getRecipe().getTitle());
	}
	
	@Test
	@DisplayName("busca por receita inexistente")
	void findByNomExistRecipeTest() {
		var ex = assertThrows(ObjectNotFound.class, () -> service
				.findByRecipe(recipeService.findById(1)));
		assertEquals("Receita 1 não encontrada", ex.getMessage());
	}
	 	
	@Test
	@DisplayName("Busca todos")
	void listAllTest() {
		List<Favorite> list = service.listAll();
		assertEquals(4, list.size()); 
	}
	
	@Test
	@DisplayName("Busca todos vazia")
	@Sql({ "classpath:/resources/sqls/limpa_tabela.sql" })
	void listAllWithEmptyTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.listAll());
		assertEquals("Nenhuma favorito cadastrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("insere ")
	void insertTest() {
		Favorite favorite = new Favorite(1, recipeService.findById(2), personService.findById(2));
		service.insert(favorite);
		assertEquals(5, service.listAll().size());
		assertEquals(1, favorite.getId());
		assertEquals("John Doe", favorite.getPerson().getName());
	}	
	
	@Test
	@DisplayName("Update ")
	void updateTest() {		
		Favorite favorite = new Favorite(2, recipeService.findById(2), personService.findById(2));
		service.update(favorite);
		Favorite favoriteUpdated = service.findById(2);
		assertEquals("John Doe", favoriteUpdated.getPerson().getName());
	}
	
	@Test
	@DisplayName("Delete")
	void deleteTest() {		
		service.delete(2);
		List<Favorite> list = service.listAll();
		assertEquals(3, list.size());
	}
	
	@Test
	@DisplayName("Delete id inexistente")
	void deleteNomExistIdTest() {
		var ex = assertThrows(ObjectNotFound.class, () -> service.delete(1));
		assertEquals("Id 1 não encontrada", ex.getMessage()); 
	}		
}
