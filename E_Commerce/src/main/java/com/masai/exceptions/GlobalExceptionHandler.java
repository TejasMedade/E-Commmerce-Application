/**
 * 
 */
package com.masai.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author tejas
 *
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

//	 GlobalException
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<MyErrorDetails> ExceptionHandler(Exception se, WebRequest req) {
//
//		MyErrorDetails err = new MyErrorDetails();
//		err.setTimestamp(LocalDateTime.now());
//		err.setMessage(se.getMessage());
//		err.setDescription(req.getDescription(false));
//
//		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.INTERNAL_SERVER_ERROR);
//
//	}

	// ResourceNotFound Exception
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<MyErrorDetails> ResourceNotFoundExceptionHandler(Exception se, WebRequest req) {

		MyErrorDetails err = new MyErrorDetails();
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(se.getMessage());
		err.setDescription(req.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.NOT_FOUND);

	}
	//Delete Later
	// DataIntegrityViolation Exception
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<MyErrorDetails> DataIntegrityViolationException(Exception se, WebRequest req) {

		MyErrorDetails err = new MyErrorDetails();
		err.setTimestamp(LocalDateTime.now());
		err.setMessage("Customer Already Exists With This Contact Number !");
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

}
