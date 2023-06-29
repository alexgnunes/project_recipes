package br.com.trier.project_recipes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.trier.project_recipes.models.Favorite;
import br.com.trier.project_recipes.models.Person;
import br.com.trier.project_recipes.models.Recipe;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer>{

	List<Favorite> findByPerson(Person person);
	List<Favorite> findByRecipe(Recipe recipe);
}
