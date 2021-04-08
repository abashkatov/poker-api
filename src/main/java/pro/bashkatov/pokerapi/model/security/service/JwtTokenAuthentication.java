package pro.bashkatov.pokerapi.model.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtTokenAuthentication implements Authentication {
    private final Collection<? extends GrantedAuthority> authorities;
    private boolean isAuthenticated;
    private final UserDetails user;
    private final Object details;

    public JwtTokenAuthentication(UserDetails user, Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
        this.user = user;
        this.details = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return details;
    }

    @Override
    public String getName() {
        if (user == null) {
            return null;
        }

        return ((UserDetails) user).getUsername();
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }
}