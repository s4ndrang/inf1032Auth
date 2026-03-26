package com.example.inf1032Auth.service;

import com.example.inf1032Auth.dto.AuthDTO;
import com.example.inf1032Auth.dto.LoginDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface JwtService {
    String getJwtToken(HttpServletRequest request);
    String generateToken(LoginDTO loginDTO);
}
