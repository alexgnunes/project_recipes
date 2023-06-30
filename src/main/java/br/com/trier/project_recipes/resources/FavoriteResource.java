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

import br.com.trier.project_recipes.models.Favorite;
import br.com.trier.project_recipes.models.dto.FavoriteDTO;
import br.com.trier.project_recipes.services.FavoriteService;
import br.com.trier.project_recipes.services.PersonService;
import br.com.trier.project_recipes.services.RecipeService;

@RestController
@RequestMapping("/favorites")
public class FavoriteResource {

	@Autowired
	private FavoriteService service;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private RecipeService recipeService;
	
	@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<FavoriteDTO> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id).toDTO());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/person/{id}")
	public ResponseEntity<List<FavoriteDTO>> findByPerson(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findByPerson(personService.findById(id))
				.stream().map((person) -> person.toDTO()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/recipe/{id}")
	public ResponseEntity<List<FavoriteDTO>> findByRecipe(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findByRecipe(recipeService.findById(id))
				.stream().map((recipe) -> recipe.toDTO()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping
	public ResponseEntity<List<FavoriteDTO>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map((user) -> user.toDTO()).toList());
	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<FavoriteDTO> insert(@RequestBody FavoriteDTO dto) {
		return ResponseEntity.ok(service.insert(new Favorite(dto, recipeService.findById(dto.getRecipeId()),
				personService.findById(dto.getPersonId()))).toDTO());
	}	
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/{id}")
	public ResponseEntity<FavoriteDTO> update(@PathVariable Integer id, @RequestBody FavoriteDTO dto) {
		Favorite favorite = new Favorite(dto, recipeService.findById(dto.getRecipeId()), 
				personService.findById(dto.getPersonId()));
		favorite.setId(id);
		return ResponseEntity.ok(service.update(favorite).toDTO());
	}	
	
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);	
		return ResponseEntity.ok().build();
	}
}
