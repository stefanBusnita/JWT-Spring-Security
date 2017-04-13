package com.jwt.security.endpoint;

import com.jwt.security.auth.jwt.JwtAuthenticationToken;
import java.io.IOException;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.security.auth.jwt.JwtSettings;
import com.jwt.security.model.token.AccessJwtToken;
import com.jwt.security.model.token.JwtToken;
import com.jwt.security.model.token.JwtTokenFactory;
import com.jwt.security.model.user.UserContext;
import java.security.spec.InvalidKeySpecException;

/**
 * RefreshTokenEndpoint
 *
 * @author stefan.busnita Ocasionally the token could be refreshed, we can get a
 * new token using this endpoint.
 */
@RestController
public class RefreshTokenEndpoint {

    @Autowired
    private JwtTokenFactory tokenFactory;
    @Autowired
    private JwtSettings jwtSettings;

    @RequestMapping(value = "/rest/auth/token", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    JwtToken refreshToken(JwtAuthenticationToken token, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, InvalidKeySpecException {
        UserContext context = (UserContext) token.getPrincipal();
        AccessJwtToken refreshedToken = tokenFactory.createAccessJwtToken(context);
        return refreshedToken;
    }
}
