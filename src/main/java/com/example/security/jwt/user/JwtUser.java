package com.example.security.jwt.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtUser implements UserDetails {

    private final Long userId;
    private final Long workerId;
    private final String username;
    private final String email;
    private final String password;
    private final boolean enable;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtUser(
            Long userId,
            Long workerId,
            String username,
            String email,
            String password,
            boolean enable,
            Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.workerId = workerId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.enable = enable;
        this.authorities = authorities;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    public Long getUserId(){
        return userId;
    }

    @JsonIgnore
    public Long getWorkerId(){ return workerId; }

    @Override
    public String getUsername(){
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return authorities;
    }

    @Override
    public boolean isEnabled(){
        return enable;
    }

    @JsonIgnore
    public String getEmail() {
        return email;
    }
}
