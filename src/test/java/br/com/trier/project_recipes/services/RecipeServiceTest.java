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
import br.com.trier.project_recipes.models.Recipe;
import br.com.trier.project_recipes.models.enums.Difficulty;
import br.com.trier.project_recipes.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/import.sql")
class RecipeServiceTest extends BaseTest{

	@Autowired
	RecipeService service;

	@Test
	@DisplayName("busca por id")
	void findByIdTest() {
		Recipe recipe = service.findById(2);
		assertNotNull(recipe);
		assertEquals(2, recipe.getId());
		assertEquals("Bolo Simples", recipe.getTitle());
	}

	@Test
	@DisplayName("busca por id inexistente")
	void findByIdNomExistIdTest() {
		var ex = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("Receita 10 não encontrada", ex.getMessage());
	}

	@Test
	@DisplayName("busca por titulo")
	void findByTitleTest() {
		List<Recipe> list = service.findByTitleIgnoreCaseOrderByTitle("Bolo Simples");
		assertEquals(1, list.size());
		assertEquals("Bolo Simples", list.get(0).getTitle());
	}

	@Test
	@DisplayName("busca por titulo inexistente")
	void findByNomExistTitleTest() {
		var ex = assertThrows(ObjectNotFound.class, () -> service.findByTitleIgnoreCaseOrderByTitle("xxx"));
		assertEquals("Nenhuma receita cadastrada", ex.getMessage());
	}

	@Test
	@DisplayName("busca por titulo contendo")
	void findByPartTitleTest() {
		List<Recipe> list = service.findByTitleContainingIgnoreCaseOrderByTitle("simples");
		assertEquals(2, list.size());
		assertEquals("Bolo Simples", list.get(0).getTitle());
	}

	@Test
	@DisplayName("busca por titulo contendo inexistente")
	void findByNomExistPartTitleTest() {
		var ex = assertThrows(ObjectNotFound.class, () -> service.findByTitleContainingIgnoreCaseOrderByTitle("xxx"));
		assertEquals("Nenhuma receita cadastrada", ex.getMessage());
	}
	
	@Test
	@DisplayName("busca por dificuldade")
	void findByDifficultyTest() {
		List<Recipe> list = service.findByDifficultyOrderByTitle(Difficulty.DIFICIL);
		assertEquals(2, list.size());
		assertEquals("Bolo Simples", list.get(0).getTitle());
	}

	@Test
	@DisplayName("busca por dificuldade inexistente")
	@Sql({ "classpath:/resources/sqls/limpa_tabela.sql" })
	void findByNomExistDifficultyTest() {
		var ex = assertThrows(ObjectNotFound.class, () -> service.findByDifficultyOrderByTitle(Difficulty.DIFICIL));
		assertEquals("Não houve uma receita com dificuldade: DIFICIL", ex.getMessage());
	}
	
	@Test
	@DisplayName("Busca todos")
	void listAllTest() {
		List<Recipe> list = service.listAll();
		assertEquals(4, list.size());
	}

	@Test
	@DisplayName("Busca todos vazia")
	@Sql({ "classpath:/resources/sqls/limpa_tabela.sql" })
	void listAllWithEmptyTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.listAll());
		assertEquals("Nenhuma receita cadastrada", exception.getMessage());
	}

	@Test
	@DisplayName("insere ")
	void insertTest() {
		Recipe recipe = new Recipe(null, "insert", null, null, null, null, null);
		service.insert(recipe);
		assertEquals(5, service.listAll().size());
		assertEquals(1, recipe.getId());
		assertEquals("insert", recipe.getTitle());
	}

	@Test
	@DisplayName("Update ")
	void updateTest() {
		Recipe recipe = new Recipe(2, "update", null, null, null, null, null);
		service.update(recipe);
		Recipe recipeUpdated = service.findById(2);
		assertEquals("update", recipeUpdated.getTitle());
	}

	@Test
	@DisplayName("Delete")
	void deleteTest() {
		service.delete(5);
		List<Recipe> list = service.listAll();
		assertEquals(3, list.size());
	}

	@Test
	@DisplayName("Delete id inexistente")
	void deleteNomExistIdTest() {
		var ex = assertThrows(ObjectNotFound.class, () -> service.delete(1));
		assertEquals("Receita 1 não encontrada", ex.getMessage());
	}
}
