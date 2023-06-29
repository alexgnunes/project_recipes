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
import br.com.trier.project_recipes.models.Person;
import br.com.trier.project_recipes.services.exceptions.DataBaseException;
import br.com.trier.project_recipes.services.exceptions.IntegrityViolation;
import br.com.trier.project_recipes.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/import.sql")
class PersonServiceTest extends BaseTest{

	@Autowired
	PersonService service;
	
	@Test
	@DisplayName("busca por id")
	void findByIdTest() {
		Person person = service.findById(2);
		assertNotNull(person);
		assertEquals(2, person.getId()); 
		assertEquals("John Doe", person.getName()); 
	}
	
	@Test
	@DisplayName("busca por id inexistente")
	void findByIdNomExistIdTest() {
		var ex = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("Usuario(a) 10 não encontrado(a)", ex.getMessage());
	}

	@Test
	@DisplayName("busca por nome contendo")
	void findByPartNameTest() {
		List<Person> list = service.findByNameContainingIgnoreCaseOrderByName("jan");
		assertEquals(1, list.size());
		assertEquals("Jane Smith", list.get(0).getName());
	}
	
	@Test
	@DisplayName("busca por nome inexistente")
	void findByNomExistNameTest() {
		var ex = assertThrows(ObjectNotFound.class, () -> service
				.findByNameContainingIgnoreCaseOrderByName("xxx"));
		assertEquals("Usuario(a) xxx não encontrado(a)", ex.getMessage());
	}	
	
	@Test
	@DisplayName("Busca todos")
	void listAllTest() {
		List<Person> list = service.listAll();
		assertEquals(3, list.size());
		assertEquals("John Doe", list.get(0).getName()); 
	}
	
	@Test
	@DisplayName("Busca todos vazia")
	@Sql({ "classpath:/resources/sqls/limpa_tabela.sql" })
	void listAllWithEmptyTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.listAll());
		assertEquals("Nenhum Usuario(a) cadastrado(a)", exception.getMessage());
	}
	
	@Test
	@DisplayName("insere ")
	void insertTest() {
		Person person = new Person(null, "insert", null, null);
		service.insert(person);
		assertEquals(4, service.listAll().size());
		assertEquals(1, person.getId());
		assertEquals("insert", person.getName());
	}	 
	
	@Test
	@DisplayName("insere com mesmo email")
	void insertWithSameEmailTest() {
		Person person = new Person(null, "insert", "john.doe@example.com", null);
		var ex = assertThrows(IntegrityViolation.class, () -> service.insert(person));
		assertEquals("john.doe@example.com já cadastrado(a)", ex.getMessage());
	}
	
	@Test
	@DisplayName("Update ")
	void updateTest() {		
		Person person = new Person(2, "update", null, null);
		service.update(person);
		Person personUpdated = service.findById(2);
		assertEquals("update", personUpdated.getName());
	}
	
	@Test
	@DisplayName("Delete")
	void deleteTest() {		
		service.delete(4);
		List<Person> list = service.listAll();
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Delete id inexistente")
	void deleteNomExistIdTest() {
		var ex = assertThrows(ObjectNotFound.class, () -> service.delete(1));
		assertEquals("Usuario(a) 1 não encontrado(a)", ex.getMessage());
	}	
	
	@Test
	@DisplayName("Delete id que é chave estrangeira")
	void deleteIdForeignkeyTest() {
		var ex = assertThrows(DataBaseException.class, () -> service.delete(2));
		System.out.println("-----------------------------------------------------------");
		System.out.println(ex.getMessage());
	}	
}

	