package com.aklbeti.account.dto;

public record LoginResponse(

        Response response,

        // String token,

        AccountResponse account
) {
}
