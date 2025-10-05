package com.codeBuddy.codeBuddy_Backend.Model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserPrincipal implements UserDetails {

    private Users user;

    public UserPrincipal(Users user){
        this.user=user;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(){
        return Collections.singleton(new SimpleGrantedAuthority("User"));
    }

    @Override
    public String getPassword(){
        return user.getPassword();
    }

    @Override
    public String getUsername(){
        return user.getUsername();
    }

    public Users getUser() {
        return user;
    }
}
