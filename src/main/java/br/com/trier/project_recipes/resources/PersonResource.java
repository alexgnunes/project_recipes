package br.com.trier.project_recipes.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.project_recipes.models.Person;
import br.com.trier.project_recipes.models.dto.PersonDTO;
import br.com.trier.project_recipes.services.PersonService;

@RestController
@RequestMapping("/persons")
public class PersonResource {

	@Autowired
	private PersonService service;
	
	@GetMapping("/{id}")
	public ResponseEntity<PersonDTO> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id).toDTO());
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<PersonDTO>> findByNameContainingIgnoreCaseOrderByName(@PathVariable String name) {
		return ResponseEntity.ok(service.findByNameContainingIgnoreCaseOrderByName(name).stream().map((user) -> user.toDTO()).toList());
	}
	
	@GetMapping
	public ResponseEntity<List<PersonDTO>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map((user) -> user.toDTO()).toList());
	}
	
	@PostMapping
	public ResponseEntity<PersonDTO> insert(@RequestBody PersonDTO dto) {
		return ResponseEntity.ok(service.insert(new Person(dto)).toDTO());
	}	
	
	@PutMapping("/{id}")
	public ResponseEntity<PersonDTO> update(@PathVariable Integer id, @RequestBody PersonDTO dto) {
		Person person = new Person(dto);
		person.setId(id);
		return ResponseEntity.ok(service.update(person).toDTO());
	}	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);	
		return ResponseEntity.ok().build();
	}
}
