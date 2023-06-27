package br.com.trier.project_recipes.services;

import java.util.List;

import br.com.trier.project_recipes.models.Commentary;

public interface CommentaryService {

	Commentary findById(Integer id);
	List<Commentary> findByContentContaining(String content);
	List<Commentary> listAll();
	Commentary insert(Commentary commentary);	
	Commentary update(Commentary commentary);
	void delete(Integer id);
}
