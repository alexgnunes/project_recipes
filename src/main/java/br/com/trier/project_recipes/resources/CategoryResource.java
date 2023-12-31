package br.com.trier.project_recipes.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.project_recipes.models.Category;
import br.com.trier.project_recipes.services.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryResource {

	@Autowired
	private CategoryService service;
	
	@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<Category> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id));
	}

	@Secured({"ROLE_USER"})
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Category>> findByNameIgnoreCaseOrderByName(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameIgnoreCaseOrderByName(name));
	}

	@Secured({"ROLE_USER"})
	@GetMapping("/partName/{name}")
	public ResponseEntity<List<Category>> findByNameContainingIgnoreCaseOrderByName(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameContainingIgnoreCaseOrderByName(name));
	}

	@Secured({"ROLE_USER"})
	@GetMapping
	public ResponseEntity<List<Category>> listAll(){
		return ResponseEntity.ok(service.listAll());
	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<Category> insert(@RequestBody Category category){
		return ResponseEntity.ok(service.insert(category));
	}
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/{id}")
	public ResponseEntity<Category> update(@PathVariable Integer id, @RequestBody Category category){
		category.setId(id);
		return ResponseEntity.ok(service.update(category));
	}
	
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);		
		return ResponseEntity.ok().build();
	}
}
