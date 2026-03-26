package com.example.inf1032Auth.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "inf1032.jwt")
@Getter
@Setter
@NoArgsConstructor
public class JwtConfigBean {
    private String secret;
    private String tokenPrefix;
    private String tokenHeader;
    private int tokenExpiration;
    private String tokenIssuer;
}