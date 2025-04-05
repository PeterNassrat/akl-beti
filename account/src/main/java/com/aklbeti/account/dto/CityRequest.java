package com.aklbeti.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CityRequest(

        @NotBlank(message = "City name is required!")
        @Size(min = 3, message = "City name must be at least 3 characters!")
        String name
) {
}
