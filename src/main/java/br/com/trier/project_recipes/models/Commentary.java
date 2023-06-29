package br.com.trier.project_recipes.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import br.com.trier.project_recipes.models.dto.CommentaryDTO;
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
public class Commentary {

	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_commentary")
	private Integer id;
	
	@Column(name = "content_commentary")
	private String content;
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Person person;
	
	@ManyToOne	
	@OnDelete(action = OnDeleteAction.CASCADE)
    private Recipe recipe;
	
	public Commentary(CommentaryDTO dto, Person person, Recipe recipe) {
		this(dto.getId(), dto.getContent(), person,recipe);
	}	
	
	public CommentaryDTO toDTO() {
		return new CommentaryDTO(id, content, person.getId(), person.getName(), recipe.getId(), recipe.getTitle());
	}
}
