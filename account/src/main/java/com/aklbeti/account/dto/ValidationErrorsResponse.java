package com.aklbeti.account.dto;

import java.util.Map;

public record ValidationErrorsResponse(
        Response response,

        int status,

        Map<String, String> validationErrors
) {
}
