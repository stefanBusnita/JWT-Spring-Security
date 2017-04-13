package com.jwt.security.model.user;

/**
 * UserRole
 *
 * @author stefan.busnita
 */
public class UserRole {

    protected String role;
    private final String rolePrefix = "ROLE_";

    public UserRole(String role) {
        this.role = role;
    }

    public String getAuthority() {
        return rolePrefix.concat(role);
    }

}
