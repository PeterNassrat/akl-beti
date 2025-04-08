package com.aklbeti.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateProfileRequest(

        @NotBlank(message = "First name is required!")
        @Size(min = 3, message = "First name must be at least 3 characters!")
        String firstName,

        @NotBlank(message = "Last name is required!")
        @Size(min = 3, message = "Last name must be at least 3 characters!")
        String lastName
) {
}
