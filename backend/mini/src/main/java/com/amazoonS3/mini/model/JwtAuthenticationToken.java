package com.amazoonS3.mini.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String token;
    private final String email;
    private final String nickname;

    public JwtAuthenticationToken(String token, String email, String nickname, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.token = token;
        this.email = email;
        this.nickname = nickname;
        setAuthenticated(true);
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return email;
    }
}
