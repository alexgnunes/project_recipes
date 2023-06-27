package br.com.trier.project_recipes.resources.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.trier.project_recipes.services.exceptions.IntegrityViolation;
import br.com.trier.project_recipes.services.exceptions.ObjectNotFound;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFound.class)
	public ResponseEntity<StandartError> getObjectNotFoundException(ObjectNotFound e, HttpServletRequest req){
		StandartError error = new StandartError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), e.getMessage(), req.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(IntegrityViolation.class)
	public ResponseEntity<StandartError> getIt(IntegrityViolation e, HttpServletRequest req){
		StandartError error = new StandartError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), e.getMessage(), req.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
}
