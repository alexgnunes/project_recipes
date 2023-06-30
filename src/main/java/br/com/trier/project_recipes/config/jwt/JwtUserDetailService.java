package br.com.trier.project_recipes.config.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.trier.project_recipes.models.Person;
import br.com.trier.project_recipes.repositories.PersonRepository;


@Component
public class JwtUserDetailService implements UserDetailsService {

	@Autowired
	private PersonRepository repository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Person person = repository.findByEmail(email).orElseThrow(null);
		return User.builder()
				.username(person.getEmail())
				.password(encoder.encode(person.getPassword()))
				.roles(person.getRoles().split(","))
				.build();
	}
}