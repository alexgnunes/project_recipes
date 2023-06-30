package br.com.trier.project_recipes.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.trier.project_recipes.models.Favorite;
import br.com.trier.project_recipes.models.Person;
import br.com.trier.project_recipes.models.Recipe;
import br.com.trier.project_recipes.repositories.FavoriteRepository;
import br.com.trier.project_recipes.services.FavoriteService;
import br.com.trier.project_recipes.services.exceptions.DataBaseException;
import br.com.trier.project_recipes.services.exceptions.ObjectNotFound;

@Service
public class FavoriteServiceImpl implements FavoriteService{

	@Autowired
	private FavoriteRepository repository;
	
	@Override
	public Favorite findById(Integer id) {
		Optional<Favorite> favorite = repository.findById(id);
		return favorite.orElseThrow(() ->new ObjectNotFound("Id %s não encontrada".formatted(id)));
	}

	@Override
	public List<Favorite> findByPerson(Person person) {		
		return repository.findByPerson(person);
	} 

	@Override
	public List<Favorite> findByRecipe(Recipe recipe) {		
		return repository.findByRecipe(recipe);
	}

	@Override
	public List<Favorite> listAll() {
		List<Favorite> list = repository.findAll();
		if (list.isEmpty()) {
			throw new ObjectNotFound("Nenhuma favorito cadastrado");
		}
		return list;
	}

	@Override
	public Favorite insert(Favorite favorite) {
		return repository.save(favorite);
	}

	@Override
	public Favorite update(Favorite favorite) {
		findById(favorite.getId());
		return repository.save(favorite);
	}

	@Override
	public void delete(Integer id) {
		try{
			Favorite favorite = findById(id);
			repository.delete(favorite);	
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Violação de integridade com o banco de dados");
		}	
	}
}
