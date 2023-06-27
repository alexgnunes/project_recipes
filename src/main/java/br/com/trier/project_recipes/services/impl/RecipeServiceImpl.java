package br.com.trier.project_recipes.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.project_recipes.models.Recipe;
import br.com.trier.project_recipes.repositories.RecipeRepository;
import br.com.trier.project_recipes.services.RecipeService;
import br.com.trier.project_recipes.services.exceptions.ObjectNotFound;

@Service
public class RecipeServiceImpl implements RecipeService{

	@Autowired
	private RecipeRepository repository;
	
	@Override
	public Recipe findById(Integer id) {
		Optional<Recipe> recipe = repository.findById(id);
		return recipe.orElseThrow(() -> new ObjectNotFound("Receita %s nao encontrada".formatted(id)));
	}

	@Override
	public List<Recipe> listAll() {
		List<Recipe> list = repository.findAll();
		if (list.isEmpty()) {
			throw new ObjectNotFound("Nenhuma receita cadastrada");
		}
		return list;
	}

	@Override
	public Recipe insert(Recipe recipe) {
		return repository.save(recipe);
	}

	@Override
	public Recipe update(Recipe recipe) {
		findById(recipe.getId());
		return repository.save(recipe);
	}

	@Override
	public void delete(Integer id) {
		Recipe recipe = findById(id);
		repository.delete(recipe);
	}
}
