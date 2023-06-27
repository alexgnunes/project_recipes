package br.com.trier.project_recipes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.trier.project_recipes.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
