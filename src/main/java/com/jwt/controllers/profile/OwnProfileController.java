package com.jwt.controllers.profile;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.security.auth.jwt.JwtAuthenticationToken;
import com.jwt.security.model.user.UserContext;

/**
 * End-point for retrieving logged-in user details from context
 * 
 * @author stefan.busnita
 */
@RestController
public class OwnProfileController {
    @RequestMapping(value="/rest/me", method=RequestMethod.GET)
    public @ResponseBody UserContext get(JwtAuthenticationToken token) {
        return (UserContext) token.getPrincipal();
    }
}
