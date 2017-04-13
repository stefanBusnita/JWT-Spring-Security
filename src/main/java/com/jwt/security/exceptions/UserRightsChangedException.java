/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jwt.security.exceptions;

import org.springframework.security.authentication.AuthenticationServiceException;

/**
 * Throw at checking if user rights changed.
 * @author stefan.busnita
 */
public class UserRightsChangedException extends AuthenticationServiceException {
    private static final long serialVersionUID = 3705043083010304496L;

    public UserRightsChangedException(String msg) {
        super(msg);
    }
}