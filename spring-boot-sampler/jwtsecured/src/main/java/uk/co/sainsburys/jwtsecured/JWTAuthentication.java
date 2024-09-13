package uk.co.sainsburys.jwtsecured;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

public class JWTAuthentication extends AbstractAuthenticationToken {
    private final String username;
    private final String jwt;

    private JWTAuthentication(String username, String ...roles) {
        super(AuthorityUtils.createAuthorityList(roles));
        super.setAuthenticated(true);
        this.jwt = null;
        this.username = username;
    }

    private JWTAuthentication(String jwt) {
        super(AuthorityUtils.NO_AUTHORITIES);
        super.setAuthenticated(false);
        this.jwt = jwt;
        this.username = null;
    }

    public static JWTAuthentication authenticated(String username, String ...roles) {
        return new JWTAuthentication(username, roles);
    }

    public static JWTAuthentication unauthenticated(String jwt) {
        return new JWTAuthentication(jwt);
    }

    @Override
    public Object getCredentials() {
        return this.getJwt();
    }

    public String getJwt() {
        return this.jwt;
    }

    @Override
    public Object getPrincipal() {
        return this.username;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        throw new RuntimeException("DON'T CHANGE THE AUTH STATUS");
    }
}
