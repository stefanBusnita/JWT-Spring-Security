package com.jwt.security.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.security.filter.CORSFilter;
import com.jwt.security.RestAuthenticationEntryPoint;
import com.jwt.security.auth.credentials.CredentialsAuthenticationProvider;
import com.jwt.security.auth.credentials.CredentialsLoginProcessingFilter;
import com.jwt.security.auth.jwt.JwtAuthenticationProvider;
import com.jwt.security.auth.jwt.JwtTokenAuthenticationProcessingFilter;
import com.jwt.security.auth.jwt.SkipPathRequestMatcher;
import com.jwt.security.auth.jwt.extractor.TokenExtractor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * WebSecurityConfig
 * Security configuration
 * @author stefan.busnita
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String JWT_TOKEN_HEADER_PARAM = "Authorization";
    public static final String CREDENTIALS_BASED_LOGIN_ENTRY_POINT = "/login";
    public static final String TOKEN_BASED_AUTH_ENTRY_POINT = "/rest/**";
    
  
    @Autowired
    private RestAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AuthenticationSuccessHandler successHandler;
    @Autowired
    private AuthenticationFailureHandler failureHandler;
    @Autowired
    private CredentialsAuthenticationProvider credentialsAuthenticationProvider;
    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    private TokenExtractor tokenExtractor;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private CORSFilter CORSFilter;


    /**
     * Build filter for DB-LDAP credentials check
     * -is a AbstractAuthenticationProcessingFilter
     * @return
     * @throws Exception 
     */
    @Bean
    protected CredentialsLoginProcessingFilter buildCredentialsLoginProcessingFilter() throws Exception {
        CredentialsLoginProcessingFilter filter = new CredentialsLoginProcessingFilter(CREDENTIALS_BASED_LOGIN_ENTRY_POINT, successHandler, failureHandler, objectMapper);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    /**
     * Build json web token authentication check
     * -is a AbstractAuthenticationProcessingFilter
     * Request Matcher is used to skip paths for TOKEN_REFRESH case and CREDENTIALS_BASED_LOGIN_ENTRY_POINT
     * @return
     * @throws Exception 
     */
    @Bean
    protected JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter() throws Exception {
        List<String> pathsToSkip = Arrays.asList( CREDENTIALS_BASED_LOGIN_ENTRY_POINT);
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, TOKEN_BASED_AUTH_ENTRY_POINT);
        JwtTokenAuthenticationProcessingFilter filter
                = new JwtTokenAuthenticationProcessingFilter(failureHandler, tokenExtractor, matcher);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    /**
     *Get authenticationManager bean 
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Add authentication providers to spring
     * @param auth 
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(credentialsAuthenticationProvider);
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    /**
     * Configure security for spring environment
     * 1.Custom authenticationEntryPoint (REST way)
     * 2.Session creation policy (Stateless)
     * 3.Permit all requests for DB-LDAP auth and JWT auth
     * 4.Add filters  CORS, DBLdapFilter, JwtTokenFilter
     * @param http
     * @throws Exception 
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //csrf is pointless for JWT, but can be activated at any point
                .headers().and()
                .exceptionHandling() 
                .authenticationEntryPoint(this.authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // stateless Session creation for a REST-JWT
                .and()
                .authorizeRequests()
                .antMatchers(CREDENTIALS_BASED_LOGIN_ENTRY_POINT).permitAll() // Login for LDAP or DB credentials check
                .and()
                .authorizeRequests()
                .and()
                .addFilterBefore(CORSFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildCredentialsLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
