package br.com.trier.project_recipes.services.exceptions;

public class IntegrityViolation extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public IntegrityViolation(String message) {
		super(message);
	}
}
