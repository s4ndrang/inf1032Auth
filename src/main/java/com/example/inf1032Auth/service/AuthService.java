package com.example.inf1032Auth.service;

import com.example.inf1032Auth.model.Auth;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthService extends UserDetailsService {
    boolean createNewAuth(Auth auth);
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    Auth findByUsername(String username);
}
