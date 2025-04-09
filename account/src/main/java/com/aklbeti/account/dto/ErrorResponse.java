package com.aklbeti.account.dto;

public record ErrorResponse(
        Response response,

        int status
) {
}
