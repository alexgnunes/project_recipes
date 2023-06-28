package br.com.trier.project_recipes.services;

import java.util.List;

import br.com.trier.project_recipes.models.Person;

public interface PersonService {

	Person findById(Integer id);

	List<Person> findByNameContainingIgnoreCaseOrderByName(String name);

	List<Person> listAll();

	Person insert(Person person);

	Person update(Person person);

	void delete(Integer id);
}
