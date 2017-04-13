package com.jwt.common.constants;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumeration of REST Error types.
 * 
 * @author stefan.busnita
 *
 */
public enum GlobalErrCodes {
    GLOBAL(1),

    AUTHENTICATION(2), JWT_TOKEN_EXPIRED(3);
    
    private int errorCode;

    private GlobalErrCodes(int errorCode) {
        this.errorCode = errorCode;
    }

    @JsonValue
    public int getErrorCode() {
        return errorCode;
    }
}
