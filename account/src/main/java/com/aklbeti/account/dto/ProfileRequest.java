package com.aklbeti.account.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record ProfileRequest(

        @NotBlank(message = "First name is required!")
        @Size(min = 3, message = "First name must be at least 3 characters")
        String firstName,

        @NotBlank(message = "Last name is required!")
        @Size(min = 3, message = "Last name must be at least 3 characters")
        String lastName,

        @NotBlank(message = "Phone number is required!")
        // @Size(min = 11, max = 11, message = "Phone number must be 11 digits!")
        @Pattern(regexp = "01[0125][0-9]{8}", message = "Invalid phone number!")
        String phoneNumber,

        @Valid
        @NotNull(message = "Address is required!")
        AddressRequest address
) {
}
