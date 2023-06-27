package br.com.trier.project_recipes.resources.exceptions;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StandartError {

	private LocalDateTime time;
	private Integer status;
	private String errorMessage;
	private String url;
}
