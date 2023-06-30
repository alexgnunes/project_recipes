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

import br.com.trier.project_recipes.models.Commentary;
import br.com.trier.project_recipes.models.dto.CommentaryDTO;
import br.com.trier.project_recipes.services.CommentaryService;
import br.com.trier.project_recipes.services.PersonService;
import br.com.trier.project_recipes.services.RecipeService;

@RestController
@RequestMapping("/commentaries")
public class CommentaryResource {

	@Autowired
	private CommentaryService service;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private RecipeService recipeService;
	
	@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<CommentaryDTO> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id).toDTO());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/content/{content}")
	public ResponseEntity<List<CommentaryDTO>> findByContentContainingIgnoreCase(@PathVariable String content){
		return ResponseEntity.ok(service.findByContentContainingIgnoreCase(content)
				.stream().map((x) -> x.toDTO()).toList());	
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping
	public ResponseEntity<List<CommentaryDTO>> listAll(){
	    return ResponseEntity.ok(service.listAll()
	            .stream().map(x -> x.toDTO()).toList());    
	}

	@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<CommentaryDTO> insert(@RequestBody CommentaryDTO dto){
		return ResponseEntity.ok(service
				.insert(new Commentary(dto, personService.findById(dto.getPersonId()),
						recipeService.findById(dto.getRecipeId()))).toDTO());
	}
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/{id}")
	public ResponseEntity<CommentaryDTO> update(@PathVariable Integer id, @RequestBody CommentaryDTO dto){
		Commentary commentary = new Commentary(dto, personService.findById(dto.getPersonId()),
				recipeService.findById(dto.getRecipeId()));
		commentary.setId(id);
		return ResponseEntity.ok(service.update(commentary).toDTO());
	}
	
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);		
		return ResponseEntity.ok().build();
	}
}
