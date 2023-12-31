package br.com.trier.project_recipes.services;

import java.util.List;

import br.com.trier.project_recipes.models.Category;

public interface CategoryService {

	Category findById(Integer id);
	List<Category> findByNameIgnoreCaseOrderByName(String title);
	List<Category> findByNameContainingIgnoreCaseOrderByName(String title);
	List<Category> listAll();
	Category insert(Category category);	
	Category update(Category category);
	void delete(Integer id);
}
