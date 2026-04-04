package com.example.inf1032Auth.dto;

import com.example.inf1032Auth.model.Auth;
import lombok.*;

import java.util.UUID;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginDTO {
    private String username;
    private String password;

}