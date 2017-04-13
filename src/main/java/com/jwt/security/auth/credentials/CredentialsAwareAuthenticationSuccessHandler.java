package com.jwt.security.auth.credentials;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.security.model.user.UserContext;
import com.jwt.security.model.token.JwtToken;
import com.jwt.security.model.token.JwtTokenFactory;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * CredentialsAwareAuthenticationSuccessHandler Called on succesfull
 * authentication
 *
 * @author stefan.busnita
 */
@Component
public class CredentialsAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper mapper;
    private final JwtTokenFactory tokenFactory;
    private final HttpServletRequest request;

    @Autowired
    public CredentialsAwareAuthenticationSuccessHandler(final ObjectMapper mapper, final JwtTokenFactory tokenFactory, HttpServletRequest request) {
        this.mapper = mapper;
        this.tokenFactory = tokenFactory;
        this.request = request;
    }

    /**
     * Get user data from provided Authentication object, create tokens, and
     * send back response to client, along with the tokens
     *
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        UserContext userContext = (UserContext) authentication.getPrincipal();
        Map<String, String> tokenMap = null;



            try {
                JwtToken accessToken = tokenFactory.createAccessJwtToken(userContext);
                
                tokenMap = new HashMap<String, String>();
                tokenMap.put("token", accessToken.getToken());
                mapper.writeValue(response.getWriter(), tokenMap);
            } catch (InvalidKeySpecException ex) {
                Logger.getLogger(CredentialsAwareAuthenticationSuccessHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        clearAuthenticationAttributes(request);
    }

    /**
     * Removes temporary authentication-related data which may have been stored
     * in the session during the authentication process.. Although session =
     * stateless was provided in config, it only affects sessions created by
     * security, the app might create a session nonetheless
     */
    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

}
