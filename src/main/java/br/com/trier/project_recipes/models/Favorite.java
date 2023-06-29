package br.com.trier.project_recipes.models;

import br.com.trier.project_recipes.models.dto.FavoriteDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (of = "id")
@Entity
public class Favorite {
	
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_favorite")
	private Integer id;
	
	@ManyToOne
	private Recipe recipe;
	
	@ManyToOne
	private Person person;
	
	public Favorite(FavoriteDTO dto, Recipe recipe, Person person) {
		this(dto.getId(), recipe, person);
	}
	
	public FavoriteDTO toDTO() {
		return new FavoriteDTO(id, recipe.getId(), recipe.getTitle(), person.getId(), person.getName());
	}
}
