package br.com.trier.project_recipes.models.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class CommentaryDTO {

	private Integer id;
	private String content;	
	private Integer personId;
	private String personName;
	private Integer recipeId;
	private String recipeTitle;
}
