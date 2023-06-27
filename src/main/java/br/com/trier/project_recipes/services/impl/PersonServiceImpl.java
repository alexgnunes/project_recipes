package br.com.trier.project_recipes.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.project_recipes.models.Person;
import br.com.trier.project_recipes.repositories.PersonRepository;
import br.com.trier.project_recipes.services.PersonService;
import br.com.trier.project_recipes.services.exceptions.ObjectNotFound;

@Service
public class PersonServiceImpl implements PersonService{

	@Autowired
	private PersonRepository repository;
	
	@Override
	public Person findById(Integer id) {
		Optional<Person> person = repository.findById(id);
		return person.orElseThrow(() -> new ObjectNotFound("Usuario %s nao encontrado".formatted(id)));
	}

	@Override
	public List<Person> listAll() {
		List<Person> list = repository.findAll();
		if (list.isEmpty()) {
			throw new ObjectNotFound("Nenhum usuario cadastrado");
		}
		return list;
	}

	@Override
	public Person insert(Person person) {
		return repository.save(person);
	}

	@Override
	public Person update(Person person) {
		findById(person.getId());
		return repository.save(person);
	}

	@Override
	public void delete(Integer id) {
		Person person = findById(id);
		repository.delete(person);
	}
}
