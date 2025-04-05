package com.aklbeti.account.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AddressRequest(

        @NotBlank(message = "Street is required!")
        @Size(min = 3, message = "Street must be at least 3 characters!")
        String street,

        @Positive(message = "Build number must be positive!")
        int buildNo,

        @Valid
        @NotNull(message = "City is required!")
        CityRequest city
) {
}
