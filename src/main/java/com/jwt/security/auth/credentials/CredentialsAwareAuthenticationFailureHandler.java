package com.jwt.security.auth.credentials;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.common.constants.GlobalErrCodes;
import com.jwt.common.message.ErrorResponse;
import com.jwt.security.exceptions.AuthMethodNotSupportedException;
import com.jwt.security.exceptions.JwtExpiredTokenException;
import com.jwt.security.exceptions.UserRightsChangedException;

/**
 * 
 * @author stefan.busnita
 */
@Component
public class CredentialsAwareAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper mapper;
    
    @Autowired
    public CredentialsAwareAuthenticationFailureHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }	
    
        /**
         * On authentication failure, check provided AuthenticationException type and create 
         * appropriate response to send back to the client
         * @param request
         * @param response
         * @param e
         * @throws IOException
         * @throws ServletException 
         */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException e) throws IOException, ServletException {
		
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType("application/json;charset=UTF-8");
		
		if (e instanceof BadCredentialsException) {
			mapper.writeValue(response.getWriter(), ErrorResponse.of("Invalid credentials.", GlobalErrCodes.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
		} else if (e instanceof JwtExpiredTokenException) {
			mapper.writeValue(response.getWriter(), ErrorResponse.of("Invalid session. Please login.", GlobalErrCodes.JWT_TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED));
		} else if (e instanceof AuthMethodNotSupportedException) {
		    mapper.writeValue(response.getWriter(), ErrorResponse.of(e.getMessage(), GlobalErrCodes.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
		} else if (e instanceof UserRightsChangedException){
                    mapper.writeValue(response.getWriter(), ErrorResponse.of("User rights changed. Please login.", GlobalErrCodes.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
                }

		mapper.writeValue(response.getWriter(), ErrorResponse.of("Failed Authentication", GlobalErrCodes.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
	}
}
