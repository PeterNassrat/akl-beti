package com.aklbeti.account.dto;

public record AccountResponse(
        long id,

        String emailAddress,

        ProfileResponse profile
) {
}
