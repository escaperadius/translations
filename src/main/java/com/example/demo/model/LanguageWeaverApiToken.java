package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LanguageWeaverApiToken {
    private String accessToken;
    private int validityInSeconds;
    private String tokenType;
    private long expiresAt;   
}
