package com.example.inf1032Auth.dto;

import com.example.inf1032Auth.model.Auth;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthDTO {
    private String id;
    private String username;
    private String password;
    private Set<String> role;

    public Auth toModel() {
        Auth auth = new Auth();
        auth.setId(UUID.fromString(this.getId()));
        auth.setUsername(this.getUsername());
        auth.setPassword(this.getPassword());
        auth.setRole(this.getRole());
        return auth;
    }
}