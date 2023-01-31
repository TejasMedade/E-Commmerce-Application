/**
 * 
 */
package com.masai.exceptions;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * @author tejas
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

//	 GlobalException
	@ExceptionHandler(Exception.class)
	public ResponseEntity<MyErrorDetails> ExceptionHandler(Exception se, WebRequest req) {

		MyErrorDetails err = new MyErrorDetails();
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(se.getMessage());
		err.setDescription(req.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	// ResourceNotFound Exception
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<MyErrorDetails> ResourceNotFoundExceptionHandler(Exception se, WebRequest req) {

		MyErrorDetails err = new MyErrorDetails();
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(se.getMessage());
		err.setDescription(req.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.NOT_FOUND);

	}

	// ResourceNotAllowedException Exception
	@ExceptionHandler(ResourceNotAllowedException.class)
	public ResponseEntity<MyErrorDetails> ResourceNotAllowedExceptionHandler(Exception se, WebRequest req) {

		MyErrorDetails err = new MyErrorDetails();
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(se.getMessage());
		err.setDescription(req.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.NOT_FOUND);

	}

	// DuplicateResourceException Exception
	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<MyErrorDetails> DuplicateResourceExceptionHandler(Exception se, WebRequest req) {

		MyErrorDetails err = new MyErrorDetails();
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(se.getMessage());
		err.setDescription(req.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.NOT_FOUND);

	}

	// Validation Exception
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e,
			WebRequest req) {

		Map<String, String> validationsErrors = new HashMap<>();

		e.getBindingResult().getAllErrors().forEach((error) -> {

			String fieldName = (((FieldError) error).getField());
			String message = error.getDefaultMessage();
			validationsErrors.put(fieldName, message);

		});

		return new ResponseEntity<Map<String, String>>(validationsErrors, HttpStatus.BAD_REQUEST);

	}

	// BadCredentialsException Exception
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<MyErrorDetails> BadCredentialsException(Exception se, WebRequest req) {

		MyErrorDetails err = new MyErrorDetails();
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(se.getMessage());
		err.setDescription(req.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.NOT_FOUND);

	}

	// FileNotFoundException Exception
	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<MyErrorDetails> FileNotFoundExceptionHandler(Exception se, WebRequest req) {

		MyErrorDetails err = new MyErrorDetails();
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(se.getMessage());
		err.setDescription(req.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.NOT_FOUND);

	}

}
