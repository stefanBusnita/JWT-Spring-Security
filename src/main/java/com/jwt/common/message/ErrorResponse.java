package com.jwt.common.message;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.jwt.common.constants.GlobalErrCodes;

/**
 * Error model for interacting with client.
 * 
 * @author stefan.busnita    
 */
public class ErrorResponse {
    // HTTP Response Status Code
    private final HttpStatus status;

    // General Error message
    private final String message;

    // Error code
    private final GlobalErrCodes errorCode;

    private final Date timestamp;

    protected ErrorResponse(final String message, final GlobalErrCodes errorCode, HttpStatus status) {
        this.message = message;
        this.errorCode = errorCode;
        this.status = status;
        this.timestamp = new java.util.Date();
    }

    public static ErrorResponse of(final String message, final GlobalErrCodes errorCode, HttpStatus status) {
        return new ErrorResponse(message, errorCode, status);
    }

    public Integer getStatus() {
        return status.value();
    }

    public String getMessage() {
        return message;
    }

    public GlobalErrCodes getErrorCode() {
        return errorCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
