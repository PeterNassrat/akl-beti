package com.aklbeti.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePasswordRequest(

        @NotBlank(message = "Old password is required!")
        String oldPassword,

        @NotBlank(message = "New password is required!")
        @Size(min = 8, message = "Password must be at least 8 characters!")
        String newPassword
) {
}
