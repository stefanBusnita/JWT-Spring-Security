package com.jwt.security.model.user;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

import com.jwt.model.sysadmin.User;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author stefan.busnita
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserContext {

    private List<GrantedAuthority> authorities;
    private User userData;
    private String username;
    private String languageCode = "RO"; //ASK FOR LANGUAGE_CODE IN LOGIN



    public UserContext(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public User getUserData() {
        return userData;
    }

    public void setUserData(User userData) {
        this.userData = userData;
    }

}
