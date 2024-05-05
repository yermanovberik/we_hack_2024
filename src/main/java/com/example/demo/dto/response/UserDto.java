package com.example.demo.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {

    @NotNull(message = "First name is required")
    private String firstname;

    @NotNull(message = "Last name is required")
    private String lastname;

    @NotNull(message = "Login is required")
    private String iin;

    @NotNull(message = "ImageUrl is required")
    private String imageUrl;

    private String priority;

    private String phoneNumber;

}
