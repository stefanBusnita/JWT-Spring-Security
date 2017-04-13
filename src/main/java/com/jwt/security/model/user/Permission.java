package com.jwt.security.model.user;

/**
 * Scopes
 * 
 * @author stefan.busnita
 */
public enum Permission {
    REFRESH_TOKEN;
    
    public String getPermission() {
        return "ROLE_" + this.name();
    }
}
