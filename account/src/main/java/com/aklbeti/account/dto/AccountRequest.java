package com.aklbeti.account.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record AccountRequest(

        @PositiveOrZero(message = "ID must be positive or zero")
        long id,

        @NotBlank(message = "Email address is required!")
        @Email(message = "Email address is not valid!")
        String emailAddress,

        @NotBlank(message = "Password is required!")
        String password,

        @Valid
        @NotNull(message = "Profile is required!")
        ProfileRequest profile
) {
}
