package com.example.inf1032Auth.controller;

import com.example.inf1032Auth.beans.JwtConfigBean;
import com.example.inf1032Auth.dto.AuthDTO;
import com.example.inf1032Auth.dto.LoginDTO;
import com.example.inf1032Auth.model.Auth;
import com.example.inf1032Auth.service.JwtService;
import com.example.inf1032Auth.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    private final JwtConfigBean jwtConfigBean;
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService,
                          JwtService jwtService,
                          JwtConfigBean jwtConfigBean,
                          AuthenticationManager authenticationManager){
        this.authService = authService;
        this.jwtService = jwtService;
        this.jwtConfigBean = jwtConfigBean;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(path = "/login", produces="application/json")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){
        System.out.println("Someone is trying to login: " + loginDTO);
        if (authenticate(loginDTO).isAuthenticated()) {
            String prefix = jwtConfigBean.getTokenPrefix();
            String token = jwtService.generateToken(loginDTO);
            System.out.println("Token: " + token);
            Auth auth = authService.findByUsername(loginDTO.getUsername());
            return ResponseEntity.ok()
                    .header(jwtConfigBean.getTokenHeader(), prefix + " " + token)
                    .body(auth.getUsername());
        } else return  ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("Error");
    }

    @PostMapping(path = "/sign-up", produces="application/json")
    public ResponseEntity<String> signUp(@RequestBody AuthDTO authDTO){
        if (usernameExists(authDTO.getUsername())) {
            return  ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Error");
        }
        boolean success = authService.createNewAuth(authDTO.toModel());
        return success?  ResponseEntity
                .ok("Success") :
                ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("Error");
    }

    @PutMapping(path = "/reset", produces="application/json")
    public ResponseEntity<String> resetPwd(@RequestBody LoginDTO resetDTO){
        System.out.println("Someone is trying to rest pwd: " + resetDTO.getUsername());
        Auth auth = authService.findByUsername(resetDTO.getUsername());
        if (auth != null) {
            auth.setPassword(resetDTO.getPassword());
            boolean response = authService.createNewAuth(auth);
            return response ? ResponseEntity.ok().body("Password reset success!") :
                    ResponseEntity.status(HttpStatus.FORBIDDEN).body("Password reset failed.");
        } else {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Password reset failed. Username does not exist.");
        }
    }

    private Authentication authenticate(LoginDTO loginDTO) throws BadCredentialsException {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername().toLowerCase().trim(),
                        loginDTO.getPassword().trim()));
    }

    private boolean usernameExists(String username) {
        return authService.findByUsername(username) != null;
    }

    @GetMapping("health")
    public String health() {
        return "OK";
    }

}