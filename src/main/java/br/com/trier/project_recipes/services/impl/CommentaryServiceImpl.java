package br.com.trier.project_recipes.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.project_recipes.models.Commentary;
import br.com.trier.project_recipes.repositories.CommentaryRepository;
import br.com.trier.project_recipes.services.CommentaryService;
import br.com.trier.project_recipes.services.exceptions.ObjectNotFound;

@Service
public class CommentaryServiceImpl implements CommentaryService{

	@Autowired
	private CommentaryRepository repository;
	
	@Override
	public Commentary findById(Integer id) {
		Optional<Commentary> commentary = repository.findById(id);
		return commentary.orElseThrow(() ->new ObjectNotFound("Comentario %s não encontrado".formatted(id)));
	}

	@Override
	public List<Commentary> listAll() {
		List<Commentary> list = repository.findAll();
		if (list.isEmpty()) {
			throw new ObjectNotFound("Nenhum comentario cadastrato");
		}
		return list;
	}

	@Override
	public Commentary insert(Commentary commentary) {
		return repository.save(commentary);
	}

	@Override
	public Commentary update(Commentary commentary) {
		findById(commentary.getId());
		return repository.save(commentary);
	}

	@Override
	public void delete(Integer id) {
		Commentary commentary = findById(id);
		repository.delete(commentary);		
	}	
}


