package br.com.trier.project_recipes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.trier.project_recipes.models.Recipe;
import br.com.trier.project_recipes.models.enums.Difficulty;

public interface RecipeRepository extends JpaRepository<Recipe, Integer>{

	List<Recipe> findByTitleOrderByTitle(String title);
	List<Recipe> findByTitleContainingOrderByTitle(String title);
	List<Recipe> findByDifficultyOrderByTitle(Difficulty difficulty);
}
