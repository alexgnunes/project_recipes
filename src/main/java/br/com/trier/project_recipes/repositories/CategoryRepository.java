package br.com.trier.project_recipes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.trier.project_recipes.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

	List<Category> findByNameOrderByName(String title);
	List<Category> findByNameContainingOrderByName(String title);
}
