package com.jwt.security.auth.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MutablePropertySources;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtSettings {
    /**
     * {@link JwtToken} will expire after this time.
     */
    private Integer tokenExpirationTime;

    /**
     * Token issuer.
     */
    private String tokenIssuer;
    
    /**
     * Key is used to sign {@link JwtToken}.
     */
    private String tokenSigningKey;


    private String jwtHeaderPrefix;

    public String getJwtHeaderPrefix() {
        return jwtHeaderPrefix;
    }

    public void setJwtHeaderPrefix(String jwtHeaderPrefix) {
        this.jwtHeaderPrefix = jwtHeaderPrefix;
    }

    public Integer getTokenExpirationTime() {
        return tokenExpirationTime;
    }
    
    public void setTokenExpirationTime(Integer tokenExpirationTime) {
        this.tokenExpirationTime = tokenExpirationTime;
    }
    
    public String getTokenIssuer() {
        return tokenIssuer;
    }
    public void setTokenIssuer(String tokenIssuer) {
        this.tokenIssuer = tokenIssuer;
    }
    
    public String getTokenSigningKey() {
        return tokenSigningKey;
    }
    
    public void setTokenSigningKey(String tokenSigningKey) {
        this.tokenSigningKey = tokenSigningKey;
    
    }
    
    @Autowired
	private ConfigurableEnvironment env;

	@PostConstruct
	public void initialize() throws IOException, InterruptedException {
		System.out.println("IN.");
		MutablePropertySources propertySources = env.getPropertySources();			
		if (env instanceof ConfigurableEnvironment) {				
			for (org.springframework.core.env.PropertySource<?> propertySource : ((ConfigurableEnvironment) env).getPropertySources()) {
				if (propertySource instanceof EnumerablePropertySource) {
					Map<String, Object> map = new HashMap<>();
					for (String key : ((EnumerablePropertySource) propertySource).getPropertyNames()) {
						//String property = (String)propertySource.getProperty(key);
						System.out.println(" Property "+key+" "+propertySource.getProperty(key));
					}
					//propertySources.replace(propertySource.getName(),new MapPropertySource(propertySource.getName(), map));
				}
			}
		}	
	}
    
}
