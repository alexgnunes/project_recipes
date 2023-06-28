package br.com.trier.project_recipes.models.dto;

import br.com.trier.project_recipes.models.enums.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter 
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {

	private Integer id;
	private String title;
	private String description;
	private Integer preparationTime;
	private Difficulty difficulty;
	private Integer personId;
	private String personName;
	private Integer categoryId;
	private String categoryName;
}
