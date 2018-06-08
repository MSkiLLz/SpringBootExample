package springboot.example.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionControllerAdvice
{
	private static final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);
	
	//Particular exceptions example
	@ExceptionHandler({ ResourceNotFoundException.class })
	public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
	  
		logger.error(ex.getLocalizedMessage());
	    return new ResponseEntity<Object>(ex.getLocalizedMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
	}
	
	//All other exceptions. Friendly error to user but log the exception
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
	  
		logger.error(ex.getLocalizedMessage());
	    return new ResponseEntity<Object>("Application error, review logs.", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	
}
