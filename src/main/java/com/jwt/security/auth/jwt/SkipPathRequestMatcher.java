package com.jwt.security.auth.jwt;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

/**
 * SkipPathRequestMatcher
 * 
 * Skip certain paths by using a request matcher
 * 
 * @author stefan.busnita
 */
public class SkipPathRequestMatcher implements RequestMatcher {
    
    private OrRequestMatcher matchers;
    private RequestMatcher processingMatcher;
    
    /**
     * 1. Use stream pipeline and convert every string path to RequestMatcher, then collect as list
     * 2. Create OrRequestMatcher and feed List of RequestMatchers
     * 3. Use an AntPathRequstMatcher to match the processingPath
     * @param pathsToSkip - as a list of strings
     * @param pathToCheck - path to process 
     */
    public SkipPathRequestMatcher(List<String> pathsToSkip, String pathToCheck) {
        Assert.notNull(pathsToSkip);
        List<RequestMatcher> m = pathsToSkip.stream().map(path -> new AntPathRequestMatcher(path)).collect(Collectors.toList()); //1
        matchers = new OrRequestMatcher(m); //2
        processingMatcher = new AntPathRequestMatcher(pathToCheck); //3
    }

    /**
     * Match actual path from request
     * 1. Match using OrRequestMatcher, check if any of the RequestMatchers match request endpoint
     * 2. Finally check if the request matches our path
     * @param request
     * @return 
     */
    @Override
    public boolean matches(HttpServletRequest request) {
        if (matchers.matches(request)) { //1
            return false;
        }
        return processingMatcher.matches(request) ? true : false; //2
    }
}
