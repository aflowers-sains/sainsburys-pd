package uk.co.sainsburys.jwtsecured;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class JWTValidationFilter extends AuthenticationFilter {

    private static final String HEADER_NAME = "authorization";

    private static final AuthenticationConverter authenticationConverter = req -> {
        if (Collections.list(req.getHeaderNames()).contains(HEADER_NAME)) {
            return JWTAuthentication.unauthenticated(req.getHeader(HEADER_NAME));
        }
        return null;
    };

    private final AuthenticationFailureHandler failureHandler = (request, response, exception) -> {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("text/plain;charset=utf8");
        response.getWriter().write(exception.getMessage());
    };

    private final AuthenticationSuccessHandler successHandler = (request, response, authentication) -> {
        // noop
    };

    public JWTValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager, authenticationConverter);
        setFailureHandler(failureHandler);
        setSuccessHandler(successHandler);
    }
}
