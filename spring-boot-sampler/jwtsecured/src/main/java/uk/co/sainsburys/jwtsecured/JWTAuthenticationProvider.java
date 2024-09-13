package uk.co.sainsburys.jwtsecured;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) {
        var authRequest = (JWTAuthentication) authentication;
        if (validJwt(authRequest.getJwt())) {
            DecodedJWT decoded = JWT.decode(authRequest.getJwt());

            // this is where we might call out to validate the user
            // perhaps a call to another micro service which is a 200 or 403, and for 200 the body of the auth details
            if(decoded.getSubject().contains("sainsburys.co.uk")) {
                return JWTAuthentication.authenticated(decoded.getSubject(),"ROLE_VALID");
            }

        }
        throw new BadCredentialsException("You are not allowed in");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JWTAuthentication.class.isAssignableFrom(authentication);
    }

    private boolean validJwt(String jwt) {
        try {
            JWT.decode(jwt);
            return true;
        } catch (JWTDecodeException e) {
            System.err.println("Failed to decode JWT token");
            e.printStackTrace();
        }
        return false;
    }
}
