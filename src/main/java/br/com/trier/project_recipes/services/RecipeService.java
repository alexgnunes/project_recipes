package br.com.trier.project_recipes.services;

import java.util.List;

import br.com.trier.project_recipes.models.Recipe;

public interface RecipeService {

	Recipe findById(Integer id);
	List<Recipe> listAll();
	Recipe insert(Recipe recipe);	
	Recipe update(Recipe recipe);
	void delete(Integer id);
}
