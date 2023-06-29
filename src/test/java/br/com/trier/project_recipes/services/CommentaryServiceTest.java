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
import br.com.trier.project_recipes.models.Commentary;
import br.com.trier.project_recipes.services.exceptions.DataBaseException;
import br.com.trier.project_recipes.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/import.sql")
class CommentaryServiceTest extends BaseTest{
	
	@Autowired
	CommentaryService service;
	
	@Test
	@DisplayName("busca por id")
	void findByIdTest() {
		Commentary commentary = service.findById(2);
		assertNotNull(commentary);
		assertEquals(2, commentary.getId()); 
		assertEquals("Ótima receita!", commentary.getContent()); 
	}
	
	@Test
	@DisplayName("busca por id inexistente")
	void findByIdNomExistIdTest() {
		var ex = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("Comentario 10 não encontrado", ex.getMessage());
	}

	@Test
	@DisplayName("busca por conteudo")
	void findByContentTest() {
		List<Commentary> list = service.findByContentContainingIgnoreCase("a");
		assertEquals(5, list.size());
		assertEquals("Ótima receita!", list.get(0).getContent());
	}
	
	@Test
	@DisplayName("busca por conteudo inexistente")
	void findByNomExistNameTest() {
		var ex = assertThrows(ObjectNotFound.class, () -> service
				.findByContentContainingIgnoreCase("xxxx"));
		assertEquals("Nenhum comentario cadastrato", ex.getMessage());
	}
		
	@Test
	@DisplayName("Busca todos")
	void listAllTest() {
		List<Commentary> list = service.listAll();
		assertEquals(6, list.size());
		assertEquals("Ótima receita!", list.get(0).getContent()); 
	}
	
	@Test
	@DisplayName("Busca todos vazia")
	@Sql({ "classpath:/resources/sqls/limpa_tabela.sql" })
	void listAllWithEmptyTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.listAll());
		assertEquals("Nenhum comentario cadastrato", exception.getMessage());
	}
	
	@Test
	@DisplayName("insere ")
	void insertTest() {
		Commentary commentary = new Commentary(null, "insert", null, null);
		service.insert(commentary);
		assertEquals(7, service.listAll().size());
		assertEquals(1, commentary.getId());
		assertEquals("insert", commentary.getContent());
	}	
	
	@Test
	@DisplayName("Update ")
	void updateTest() {		
		Commentary commentary = new Commentary(2, "update", null, null);
		service.update(commentary);
		Commentary commentaryUpdated = service.findById(2);
		assertEquals("update", commentaryUpdated.getContent());
	}
	
	@Test
	@DisplayName("Delete")
	void deleteTest() {		
		service.delete(2);
		List<Commentary> list = service.listAll();
		assertEquals(5, list.size());
	}
	
	@Test
	@DisplayName("Delete id inexistente")
	void deleteNomExistIdTest() {
		var ex = assertThrows(ObjectNotFound.class, () -> service.delete(1));
		assertEquals("Comentario 1 não encontrado", ex.getMessage());
	}	
	
	@Test
	@DisplayName("Delete id que é chave estrangeira")
	void deleteIdForeignkeyTest() {
		var ex = assertThrows(DataBaseException.class, () -> service.delete(2));
		System.out.println("-----------------------------------------------------------");
		System.out.println(ex.getMessage());
	}	
}
