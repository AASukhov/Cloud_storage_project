package com.example.diploma.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthResponseDto {
    @JsonProperty("auth-token")
    private String accessToken;

    public AuthResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }

}
