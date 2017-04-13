package com.jwt.security.auth.jwt.extractor;

import com.jwt.security.auth.jwt.JwtSettings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

/**
 * An implementation of {@link TokenExtractor} extracts token from
 * Authorization: Bearer scheme.
 * 
 * @author stefan.busnita
 */
@Component
public class JwtHeaderTokenExtractor implements TokenExtractor {

    private JwtSettings jwtSettings;

    @Autowired
    public JwtHeaderTokenExtractor(JwtSettings jwtSettings){
            this.jwtSettings = jwtSettings;
    }

    @Override
    public String extract(String header) {
        if (StringUtils.isBlank(header)) {
            throw new AuthenticationServiceException("Authorization header cannot be blank!");
        }

        if (header.length() < jwtSettings.getJwtHeaderPrefix().length()) {
            throw new AuthenticationServiceException("Invalid authorization header size.");
        }

        return header.substring(jwtSettings.getJwtHeaderPrefix().length(), header.length());
    }
}
