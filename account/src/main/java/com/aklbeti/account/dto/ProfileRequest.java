package com.aklbeti.account.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ProfileRequest(

        @NotBlank(message = "First name is required!")
        @Size(min = 3, message = "First name must be at least 3 characters")
        String firstName,

        @NotBlank(message = "Last name is required!")
        @Size(min = 3, message = "Last name must be at least 3 characters")
        String lastName,

        @Valid
        @NotEmpty(message = "At least one address is required")
        List<@Valid AddressRequest> addresses
) {
}
