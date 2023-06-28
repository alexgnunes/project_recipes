package br.com.trier.project_recipes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.trier.project_recipes.models.Commentary;

public interface CommentaryRepository extends JpaRepository<Commentary, Integer>{

	List<Commentary> findByContentContainingIgnoreCase(String content);

}
