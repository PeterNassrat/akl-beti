package com.aklbeti.account.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record RegistrationRequest(

        @NotBlank(message = "Email address is required!")
        @Email(message = "Email address is not valid!")
        String emailAddress,

        @NotBlank(message = "Password is required!")
        @Size(min = 8, message = "Password must be at least 8 characters!")
        String password,

        @Valid
        @NotNull(message = "Profile is required!")
        ProfileRequest profile
) {
}
