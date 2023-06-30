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

import br.com.trier.project_recipes.models.Recipe;
import br.com.trier.project_recipes.models.dto.RecipeDTO;
import br.com.trier.project_recipes.models.enums.Difficulty;
import br.com.trier.project_recipes.services.CategoryService;
import br.com.trier.project_recipes.services.PersonService;
import br.com.trier.project_recipes.services.RecipeService;

@RestController
@RequestMapping("/recipes")
public class RecipeResource {

	@Autowired
	private RecipeService service;
	
	@Autowired
	private PersonService personservice;
	
	@Autowired
	private CategoryService categoryservice;
	
	@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<RecipeDTO> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id).toDTO());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/title/{title}")
	public ResponseEntity<List<RecipeDTO>> findByTitleIgnoreCaseOrderByTitle(@PathVariable String title){
		return ResponseEntity.ok(service.findByTitleIgnoreCaseOrderByTitle(title)
				.stream().map((recipe) -> recipe.toDTO()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/partTitle/{title}")
	public ResponseEntity<List<RecipeDTO>> findByTitleContainingIgnoreCaseOrderByTitle(@PathVariable String title){
		return ResponseEntity.ok(service.findByTitleContainingIgnoreCaseOrderByTitle(title)
				.stream().map((recipe) -> recipe.toDTO()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/difficulty/{difficulty}")
	public ResponseEntity<List<RecipeDTO>> findByDifficultyOrderByTitle(@PathVariable Difficulty difficulty){
		return ResponseEntity.ok(service.findByDifficultyOrderByTitle(difficulty)
				.stream().map((recipe) -> recipe.toDTO()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping
	public ResponseEntity<List<RecipeDTO>> listAll(){
		return ResponseEntity.ok(service.listAll().stream().map((recipe) -> recipe.toDTO()).toList());
	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<RecipeDTO> insert(@RequestBody RecipeDTO dto){
		return ResponseEntity.ok(service.insert(new Recipe(dto, 
				personservice.findById(dto.getPersonId()),
				categoryservice.findById(dto.getCategoryId()))).toDTO());
	}	
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/{id}")
	public ResponseEntity<Recipe> update(@PathVariable Integer id, @RequestBody RecipeDTO dto){
		Recipe recipe = new Recipe(dto, 
				personservice.findById(dto.getPersonId()),
				categoryservice.findById(dto.getCategoryId()));
		recipe.setId(id);
		return ResponseEntity.ok(service.update(recipe));
	}
	
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);		
		return ResponseEntity.ok().build();
	}
}
