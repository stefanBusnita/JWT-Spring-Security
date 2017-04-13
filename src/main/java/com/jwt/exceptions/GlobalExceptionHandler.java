/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jwt.exceptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.jwt.common.constants.GlobalErrCodes;
import com.jwt.common.message.ErrorResponse;
import com.jwt.common.message.MessageProvider;

/**
 *
 * @author stefan.busnita
 */
@ControllerAdvice
@Component
public class GlobalExceptionHandler {

    private static final Logger _LOGGER = Logger.getLogger(GlobalExceptionHandler.class.getName());
    
    @Autowired
	MessageProvider messageProvider;
    
    @Autowired
    ErrorResponseFactory errorResponseFactory;
    
    
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map handleException(BindException exception) {
        _LOGGER.log(Level.INFO, (Supplier<String>) exception);
        List<String> errors = new ArrayList<>();
        for (FieldError fe : exception.getFieldErrors()) {
            errors.add(fe.getDefaultMessage());
        }
        return error(errors);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map handleAccessDeniedException(AccessDeniedException exception) {

        return error("Access denied, insufficient rights.");
    }

    
    @ExceptionHandler()
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorResponse handleException(Exception exception, HttpServletResponse httpResponse) {
    	exception.printStackTrace();	
    	
    	ErrorResponse errorResponse = errorResponseFactory.getErrorResponse();
    	if(errorResponse != null && errorResponse.getMessage() == null){
    		String msg = messageProvider.getDefaultErrorMessage();
    		errorResponse = ErrorResponse.of(msg, GlobalErrCodes.GLOBAL, HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    	errorResponseFactory.unregisterErrorResponse();
    	
    	httpResponse.addHeader("IS-FLASH", "true");
    	return errorResponse;
	}

    private Map error(Object message) {
        return Collections.singletonMap("response", message);
    }

}

