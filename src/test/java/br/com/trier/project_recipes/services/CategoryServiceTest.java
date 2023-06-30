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
import br.com.trier.project_recipes.services.exceptions.DataBaseException;
import br.com.trier.project_recipes.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/import.sql")
class CategoryServiceTest extends BaseTest {

	@Autowired
	CategoryService service;

	@Test
	@DisplayName("busca por id")
	void findByIdTest() {
		Category category = service.findById(2);
		assertNotNull(category);
		assertEquals(2, category.getId());
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
	void findByNameTest() {
		List<Category> list = service.findByNameIgnoreCaseOrderByName("Bolos");
		assertEquals(1, list.size());
		assertEquals("Bolos", list.get(0).getName());
	}

	@Test
	@DisplayName("busca por nome inexistente")
	void findByNomExistNameTest() {
		var ex = assertThrows(ObjectNotFound.class, () -> service.findByNameIgnoreCaseOrderByName("Bo"));
		assertEquals("Categoria Bo não encontrada", ex.getMessage());
	}

	@Test
	@DisplayName("busca por nome contendo")
	void findByPartNameTest() {
		List<Category> list = service.findByNameContainingIgnoreCaseOrderByName("s");
		assertEquals(5, list.size());
		assertEquals("Bolos", list.get(0).getName());
	}

	@Test
	@DisplayName("busca por nome contendo inexistente")
	void findByNomExistPartNameTest() {
		var ex = assertThrows(ObjectNotFound.class, () -> service.findByNameContainingIgnoreCaseOrderByName("xxx"));
		assertEquals("Categoria xxx não encontrada", ex.getMessage()); 
	}

	@Test
	@DisplayName("Busca todos")
	void listAllTest() {
		List<Category> list = service.listAll();
		assertEquals(5, list.size());
		assertEquals("Bolos", list.get(0).getName());
	}

	@Test
	@DisplayName("Busca todos vazia")
	@Sql({ "classpath:/resources/sqls/limpa_tabela.sql" })
	void listAllWithEmptyTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.listAll());
		assertEquals("Nenhuma categoria cadastrada", exception.getMessage());
	}

	@Test
	@DisplayName("insere ")
	void insertTest() {
		Category category = new Category(null, "insert");
		service.insert(category);
		assertEquals(6, service.listAll().size());
		assertEquals(1, category.getId());
		assertEquals("insert", category.getName());
	}

	@Test
	@DisplayName("Update ")
	void updateTest() {
		Category category = new Category(2, "update");
		service.update(category);
		Category categoryUpdated = service.findById(2);
		assertEquals("update", categoryUpdated.getName());
	}

	@Test
	@DisplayName("Delete")
	void deleteTest() {
		service.delete(6);
		List<Category> list = service.listAll();
		assertEquals(4, list.size());
	}

	@Test
	@DisplayName("Delete id inexistente")
	void deleteNomExistIdTest() {
		var ex = assertThrows(ObjectNotFound.class, () -> service.delete(1));
		assertEquals("Categoria 1 não encontrada", ex.getMessage());
	}

	@Test
	@DisplayName("Delete id que é chave estrangeira")
	void deleteIdForeignkeyTest() {
		var ex = assertThrows(DataBaseException.class, () -> service.delete(2));
		assertEquals("Violação de integridade com o banco de dados", ex.getMessage());
	}
}
