package com.example.inf1032Auth.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;
import java.util.UUID;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "auth")
public class Auth {
    private UUID id;
    private String username;
    private String password;
    private Set<String> role;
}