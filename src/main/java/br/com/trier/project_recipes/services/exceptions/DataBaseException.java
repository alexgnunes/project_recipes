package br.com.trier.project_recipes.services.exceptions;

public class DataBaseException  extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public DataBaseException (String message) {
		super(message);
	}
}
