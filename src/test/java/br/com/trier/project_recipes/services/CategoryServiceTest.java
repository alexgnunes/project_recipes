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
import br.com.trier.project_recipes.models.Category;
import br.com.trier.project_recipes.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/import.sql")
class CategoryServiceTest extends BaseTest{

	
	@Autowired
	CategoryService service;
	
	@Test
	@DisplayName("busca por id")
	void findByIdTest() {
		Category category = service.findById(1);
		assertNotNull(category);
		assertEquals(1, category.getId()); 
		assertEquals("Bolos", category.getName()); 
	}
	
	@Test
	@DisplayName("busca por id inexistente")
	void findByIdNomExistIdTest() {
		var ex = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("Categoria 10 não encontrada", ex.getMessage());
	}

	@Test
	@DisplayName("busca por  nome")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void findTeamByPartNameTest() {
		List<Category> list = service.findByNameIgnoreCaseOrderByName("Bolo Simples");
		assertEquals(1, list.size());
		assertEquals("Bolo Simples", list.get(0).getName());
	}
//	List<Category> findByNameIgnoreCaseOrderByName(String title);
//	List<Category> findByNameContainingIgnoreCaseOrderByName(String title);
//	List<Category> listAll();
//	Category insert(Category category);	
//	Category update(Category category);
//	void delete(Integer id);

}
