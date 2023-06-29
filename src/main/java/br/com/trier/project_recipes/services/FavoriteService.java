package br.com.trier.project_recipes.services;

import java.util.List;

import br.com.trier.project_recipes.models.Favorite;
import br.com.trier.project_recipes.models.Person;
import br.com.trier.project_recipes.models.Recipe;

public interface FavoriteService {

	Favorite findById(Integer id);
	List<Favorite> findByPerson(Person person);
	List<Favorite> findByRecipe(Recipe recipe);
	List<Favorite> listAll();
	Favorite insert(Favorite favorite);	
	Favorite update(Favorite favorite);
	void delete(Integer id);
}
