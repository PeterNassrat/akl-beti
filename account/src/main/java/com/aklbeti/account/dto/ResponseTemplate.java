package com.aklbeti.account.dto;

public record ResponseTemplate(
        boolean success,

        String message,

        AccountResponse account
) {
}
