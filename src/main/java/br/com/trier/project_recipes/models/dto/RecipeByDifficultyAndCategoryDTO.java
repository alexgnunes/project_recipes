package br.com.trier.project_recipes.models.dto;

import java.util.List;

import br.com.trier.project_recipes.models.Recipe;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter 
@NoArgsConstructor
@AllArgsConstructor
public class RecipeByDifficultyAndCategoryDTO {

	private String category;
	private String difficulty;
	List<Recipe> recipeFilter;
	
}
