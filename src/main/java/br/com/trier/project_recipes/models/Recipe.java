package br.com.trier.project_recipes.models;

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
	private Person person;
	
	@ManyToOne
	private Category category;	
}
