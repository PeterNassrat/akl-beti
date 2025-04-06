package com.aklbeti.account.dto;

import java.util.List;

public record ProfileResponse(
        String firstName,

        String lastName,

        List<AddressResponse> addresses
) {
}
