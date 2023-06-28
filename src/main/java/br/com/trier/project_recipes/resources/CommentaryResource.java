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

import br.com.trier.project_recipes.models.Commentary;
import br.com.trier.project_recipes.services.CommentaryService;

@RestController
@RequestMapping("/commentaries")
public class CommentaryResource {

	@Autowired
	private CommentaryService service;
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Commentary> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping("/content/{content}")
	public ResponseEntity<List<Commentary>> findByContentContainingIgnoreCase(@PathVariable String content){
		return ResponseEntity.ok(service.findByContentContainingIgnoreCase(content));
	}
	
	@GetMapping
	public ResponseEntity<List<Commentary>> listAll(){
		return ResponseEntity.ok(service.listAll());
	}
	
	@PostMapping
	public ResponseEntity<Commentary> insert(@RequestBody Commentary commentary){
		return ResponseEntity.ok(service.insert(commentary));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Commentary> update(@PathVariable Integer id, @RequestBody Commentary commentary){
		commentary.setId(id);
		return ResponseEntity.ok(service.update(commentary));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);		
		return ResponseEntity.ok().build();
	}
}
