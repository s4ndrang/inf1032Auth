package com.example.inf1032Auth.service.impl;

import com.example.inf1032Auth.model.Auth;
import com.example.inf1032Auth.model.CustomUserDetails;
import com.example.inf1032Auth.repository.AuthRepository;
import com.example.inf1032Auth.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service("main")
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder encoder;

    public AuthServiceImpl(AuthRepository authRepository,
                           PasswordEncoder encoder) {
        this.authRepository = authRepository;
        this.encoder = encoder;
    }

    @Override
    public boolean createNewAuth(Auth auth) {
        auth.setPassword(encoder.encode(auth.getPassword().trim()));
        authRepository.save(auth);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Auth auth = authRepository.findByUsername(username);
        if(auth == null){
            log.error("Auth not found: " + username);
            throw new UsernameNotFoundException("could not found user..!!");
        }
        log.info("Authenticated with Success!!!");
        return new CustomUserDetails(auth);
    }

    public Auth findByUsername(String username) {
        return authRepository.findByUsername(username);
    }
}