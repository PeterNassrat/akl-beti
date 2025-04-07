package com.aklbeti.account.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @NotBlank(message = "Email address is required!")
        String emailAddress,

        @NotBlank(message = "Password is required!")
        String password
) {
}
