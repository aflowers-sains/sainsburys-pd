package uk.co.sainsburys.jwtsecured;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

public class JWTAccountConfigurer  extends AbstractHttpConfigurer<JWTAccountConfigurer, HttpSecurity> {
    @Override
    public void init(HttpSecurity http) {
        http.authenticationProvider(new JWTAuthenticationProvider());
    }

    @Override
    public void configure(HttpSecurity http) {
        var authManager = http.getSharedObject(AuthenticationManager.class);
        http.addFilterBefore(new JWTValidationFilter(authManager), AuthorizationFilter.class);
    }
}
