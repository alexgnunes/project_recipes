package br.com.trier.project_recipes.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.project_recipes.models.Category;
import br.com.trier.project_recipes.repositories.CategoryRepository;
import br.com.trier.project_recipes.services.CategoryService;
import br.com.trier.project_recipes.services.exceptions.ObjectNotFound;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepository repository;
	
	@Override
	public Category findById(Integer id) {
		Optional<Category> category = repository.findById(id);
		return category.orElseThrow(() ->new ObjectNotFound("Categoria %s não encontrada".formatted(id)));
	}

	@Override
	public List<Category> listAll() {
		List<Category> list = repository.findAll();
		if (list.isEmpty()) {
			throw new ObjectNotFound("Nenhuma categoria cadastrata");
		}
		return list;
	}

	@Override
	public Category insert(Category category) {
		return repository.save(category);
	}

	@Override
	public Category update(Category category) {
		findById(category.getId());
		return repository.save(category);
	}

	@Override
	public void delete(Integer id) {
		Category category = findById(id);
		repository.delete(category);
		
	}	
}
