package com.example.demo.security.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    @JsonProperty("iin")
    @NotNull(message = "iin is required")
    private String iin;

    @JsonProperty("password")
    @NotNull(message = "Password is required")
    public String password;

}
