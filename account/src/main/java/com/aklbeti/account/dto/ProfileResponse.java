package com.aklbeti.account.dto;

import java.util.List;

public record ProfileResponse(
        String firstName,

        String lastName,

        String phoneNumber,

        AddressResponse address
) {
}
