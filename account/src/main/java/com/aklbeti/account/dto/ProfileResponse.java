package com.aklbeti.account.dto;

public record ProfileResponse(
        String firstName,

        String lastName,

        String phoneNumber,

        AddressResponse address
) {
}
