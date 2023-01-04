/**
 * 
 */
package com.masai.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * @author tejas
 *
 */


public class GlobalExceptionHandler {

	
	//GlobalException
	@ExceptionHandler(Exception.class)
	public ResponseEntity<MyErrorDetails> ExceptionHandler(Exception se, WebRequest req) {

		MyErrorDetails err = new MyErrorDetails();
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(se.getMessage());
		err.setDescription(req.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.NOT_FOUND);

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
	
}
