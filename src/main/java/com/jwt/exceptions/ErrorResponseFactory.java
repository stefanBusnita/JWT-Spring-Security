package com.jwt.exceptions;

import org.springframework.stereotype.Component;
import com.jwt.common.message.ErrorResponse;

/**
 * 
 * @author stefan.busnita
 *
 */
@Component
public class ErrorResponseFactory {

	private ErrorResponse errorResponse;
	
	public void registerErrorResponse(ErrorResponse errorResponse){
		this.errorResponse = errorResponse;
	}
	
	public void unregisterErrorResponse(){
		this.errorResponse = null;
	}
	
	public ErrorResponse getErrorResponse(){
		return this.errorResponse;
	}
}
