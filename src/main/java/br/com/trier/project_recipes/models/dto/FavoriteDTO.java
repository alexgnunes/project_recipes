package br.com.trier.project_recipes.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter 
@NoArgsConstructor 
@AllArgsConstructor
public class FavoriteDTO {
	
	private Integer id;
	private Integer recipeId;
	private String recipeTitle;
	private Integer personId;
	private String personName;

}
