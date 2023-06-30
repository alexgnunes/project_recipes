package br.com.trier.project_recipes.models;

import br.com.trier.project_recipes.models.dto.PersonDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
public class Person {
	
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_person")
	private Integer id;

	@Column(name = "nome_person")
	private String name;

	@Column(name = "email_person", unique = true)
	private String email;

	@Column(name = "senha_person")
	private String password;
	
	@Column(name = "permissoes_usuario")
	private String roles;
	
	public Person(PersonDTO dto) {
		this(dto.getId(),dto.getName(),dto.getEmail(),dto.getPassword(), dto.getRoles());
	}
	
	public PersonDTO toDTO() {
		return new PersonDTO(id, name, email, password, roles);		
	}
	
}
