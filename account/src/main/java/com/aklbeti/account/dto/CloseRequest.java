package com.aklbeti.account.dto;

import jakarta.validation.constraints.NotBlank;

public record CloseRequest(

        @NotBlank(message = "Password is required!")
        String password
) {
}
