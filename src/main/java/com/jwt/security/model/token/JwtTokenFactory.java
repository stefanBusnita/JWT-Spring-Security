package com.jwt.security.model.token;

import com.jwt.model.sysadmin.User;
import com.jwt.security.auth.cryptographics.Crypto;
import com.jwt.security.auth.jwt.JwtSettings;
import com.jwt.security.model.user.UserData;
import com.jwt.security.model.user.UserContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.spec.InvalidKeySpecException;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Factory class that should be always used to create {@link JwtToken}.
 *
 * @author stefan.busnita
 */
@Component
public class JwtTokenFactory {

    private final JwtSettings settings;

    @Autowired
    public JwtTokenFactory(JwtSettings settings) {
        this.settings = settings;
    }
    
    @Autowired
    Crypto crypto;

    /**
     * Factory method for issuing new JWT Tokens.
     *
     * @return
     */
    public AccessJwtToken createAccessJwtToken(UserContext userContext) throws InvalidKeySpecException {

        if (userContext.getAuthorities() == null || userContext.getAuthorities().isEmpty()) {
            throw new IllegalArgumentException("User doesn't have any privileges");
        }


        Claims claims = Jwts.claims().setSubject("username");
        fillClaims(claims, userContext);

        DateTime currentTime = new DateTime();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(settings.getTokenIssuer())
                .setIssuedAt(currentTime.toDate())
                .setExpiration(currentTime.plusMinutes(settings.getTokenExpirationTime()).toDate())
                .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
                .compact();

        return new AccessJwtToken(token, claims);
    }

    private void fillClaims(Claims claims, UserContext userContext) throws InvalidKeySpecException {
        User loginData = userContext.getUserData();

        claims.put("scopes", userContext.getAuthorities().stream().map(s -> s.toString()).collect(Collectors.toList()));
        claims.put("username", userContext.getUsername());
    }
}
