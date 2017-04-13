package com.jwt.security.auth.credentials;


import com.jwt.model.sysadmin.Rights;
import com.jwt.model.sysadmin.User;
import com.jwt.security.auth.PasswordEncryption;
import com.jwt.security.model.user.UserContext;
import com.jwt.security.model.user.UserRole;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.jwt.services.utils.sysadmin.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

/**
 *
 * @author stefan.busnita Responsible for processing the Authentication
 * implementation send accross application Check if user is available in
 * repository, check required fields and throw exceptions accordignly
 */
@Component
public class CredentialsAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    PasswordEncryption passwordEncryption;

    @Autowired
    UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UserContext userContext = new UserContext();
        User retrievedUser = null;


        LoginRequest loginRequest = (LoginRequest) authentication.getPrincipal();
        String password = null;
        String username = null;

        try {
            password = (String) authentication.getCredentials();
            username = loginRequest.getUsername();
        } catch (NullPointerException ex) {
            //already checked in initial login processing filter, should be removed.
            throw new InsufficientAuthenticationException("Password and username are mandatory fields.");
        }

        try {
         retrievedUser = userService.findByUsername(username);

        } catch (NullPointerException ex) {
            throw new InsufficientAuthenticationException("User not found: "+loginRequest.getUsername());
        }
        if(retrievedUser!=null){
                //PASSWORD SHOULD BE HASHED IN DB, AND NOT SENT IN PURE FORM
            //PLEASE ADD SPRING PASSWORD ENCRYPTION
            if(retrievedUser.getPassword().equals(password)){
                userContext.setUserData(retrievedUser);
                userContext.setAuthorities(getAuthorities(retrievedUser));
            }else{
                throw new BadCredentialsException("User not found: " + loginRequest.getUsername());
            }
        }else{
            throw new BadCredentialsException("User not found: " + loginRequest.getUsername());
        }

        return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    private List<GrantedAuthority> getAuthorities(User retrievedUser) {

        List<UserRole> roles = new ArrayList<UserRole>();
        Set<Rights> registeredUserRights = retrievedUser.getUserRights();

        for(Rights right: registeredUserRights){
            roles.add(new UserRole(right.getRightName()));
        }

        List<GrantedAuthority> authorities = roles.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());

        return authorities;
    }

}
