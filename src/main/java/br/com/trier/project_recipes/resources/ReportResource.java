package br.com.trier.project_recipes.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.project_recipes.models.Category;
import br.com.trier.project_recipes.models.Recipe;
import br.com.trier.project_recipes.models.dto.RecipeByDifficultyAndCategoryDTO;
import br.com.trier.project_recipes.models.enums.Difficulty;
import br.com.trier.project_recipes.services.CategoryService;
import br.com.trier.project_recipes.services.RecipeService;

@RestController
@RequestMapping("/reports")
public class ReportResource {
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	RecipeService recipeService;

	@Secured({"ROLE_USER"})
	@GetMapping("/recipe-difficult-category/{difficulty}/{categoryId}")
	public ResponseEntity<RecipeByDifficultyAndCategoryDTO> findRecipeByDifficultyAndCategory(
	        @PathVariable Difficulty difficulty, @PathVariable Integer categoryId) {
	    Category category = categoryService.findById(categoryId);

	    List<Recipe> recipes = recipeService.findByDifficultyOrderByTitle(difficulty);
	    List<Recipe> recipeFilter = recipes.stream()
	            .filter(recipe -> recipe.getCategory().equals(category))
	            .collect(Collectors.toList());

	    RecipeByDifficultyAndCategoryDTO result = new RecipeByDifficultyAndCategoryDTO(
	            category.getName(), difficulty.name(), recipeFilter);
	    return ResponseEntity.ok(result);
	}
}
