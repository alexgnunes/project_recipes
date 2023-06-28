package br.com.trier.project_recipes.services;

import java.util.List;

import br.com.trier.project_recipes.models.Recipe;
import br.com.trier.project_recipes.models.enums.Difficulty;

public interface RecipeService {

	Recipe findById(Integer id);
	List<Recipe> findByTitleIgnoreCaseOrderByTitle(String title);
	List<Recipe> findByTitleContainingIgnoreCaseOrderByTitle(String title);
	List<Recipe> findByDifficultyOrderByTitle(Difficulty difficulty);
	List<Recipe> listAll();
	Recipe insert(Recipe recipe);	
	Recipe update(Recipe recipe);
	void delete(Integer id);
}
