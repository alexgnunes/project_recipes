package br.com.trier.project_recipes.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.trier.project_recipes.models.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {

	Optional<Person> findByEmail(String email);

	List<Person> findByNameContainingIgnoreCaseOrderByName(String name);
}
