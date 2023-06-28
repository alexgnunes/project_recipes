package br.com.trier.project_recipes.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import br.com.trier.project_recipes.models.dto.RecipeDTO;
import br.com.trier.project_recipes.models.enums.Difficulty;
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
@EqualsAndHashCode(of = "id")
@Entity
public class Recipe {

	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_recipe")
	private Integer id;

	@Column(name = "title_recipe")
	private String title;

	@Column(name = "description_recipe")
	private String description;

	@Column(name = "preparation_Time_recipe")
	private Integer preparationTime;

	@Column(name = "difficulty_recipe")
	private Difficulty difficulty;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Person person;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Category category;

	public Recipe(RecipeDTO dto, Person person, Category category) {
	this(dto.getId(), dto.getTitle(), dto.getDescription(), dto.getPreparationTime(), dto.getDifficulty(),person, category);
	}
	
	public RecipeDTO toDTO() {
		return new RecipeDTO(id, title, description, preparationTime, difficulty,
				person.getId(), person.getName(), category.getId(), category.getName());
		
	}

}
