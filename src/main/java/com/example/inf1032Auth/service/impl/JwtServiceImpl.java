package com.example.inf1032Auth.service.impl;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWT;

import com.example.inf1032Auth.beans.JwtConfigBean;
import com.example.inf1032Auth.dto.LoginDTO;
import com.example.inf1032Auth.model.CustomUserDetails;
import com.example.inf1032Auth.service.AuthService;
import com.example.inf1032Auth.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtServiceImpl implements JwtService {

    private final JwtConfigBean jwtConstants;
    private final AuthService authService;

    public JwtServiceImpl(JwtConfigBean jwtConstants, AuthService authService){
        this.jwtConstants = jwtConstants;
        this.authService = authService;
    }


    @Override
    public String getJwtToken(HttpServletRequest request) {
        String headerAuth = request.getHeader(jwtConstants.getTokenHeader());
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(jwtConstants.getTokenPrefix())){
            return headerAuth.replace(jwtConstants.getTokenPrefix(), "").trim();
        }
        return null;
    }

    @Override
    public String generateToken(LoginDTO loginDTO) {
        Calendar exp = Calendar.getInstance();
        Calendar issDate = (Calendar) exp.clone();
        exp.add(Calendar.HOUR, jwtConstants.getTokenExpiration());

        CustomUserDetails user = (CustomUserDetails) authService.loadUserByUsername(loginDTO.getUsername());
        List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        UUID id = user.getId();

        return JWT.create()
                .withSubject((loginDTO.getUsername()))
                .withIssuedAt(issDate.getTime())
                .withExpiresAt(exp.getTime())
                .withIssuer(jwtConstants.getTokenIssuer())
                .withClaim("userId", id.toString())
                .withClaim("role", roles)
                .sign(Algorithm.HMAC256(jwtConstants.getSecret()));
    }
}
