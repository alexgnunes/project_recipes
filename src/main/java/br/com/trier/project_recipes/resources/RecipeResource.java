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

import br.com.trier.project_recipes.models.Recipe;
import br.com.trier.project_recipes.services.RecipeService;

@RestController
@RequestMapping("/recipes")
public class RecipeResource {

	@Autowired
	private RecipeService service;
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Recipe> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping("/title/{title}")
	public ResponseEntity<List<Recipe>> findByTitleOrderByTitle(@PathVariable String title){
		return ResponseEntity.ok(service.findByTitleOrderByTitle(title));
	}
	
	@GetMapping("/partTitle/{title}")
	public ResponseEntity<List<Recipe>> findByTitleContainingOrderByTitle(@PathVariable String title){
		return ResponseEntity.ok(service.findByTitleContainingOrderByTitle(title));
	}
	
	@GetMapping
	public ResponseEntity<List<Recipe>> listAll(){
		return ResponseEntity.ok(service.listAll());
	}
	
	@PostMapping
	public ResponseEntity<Recipe> insert(@RequestBody Recipe recipe){
		return ResponseEntity.ok(service.insert(recipe));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Recipe> update(@PathVariable Integer id, @RequestBody Recipe recipe){
		recipe.setId(id);
		return ResponseEntity.ok(service.update(recipe));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);		
		return ResponseEntity.ok().build();
	}
}
