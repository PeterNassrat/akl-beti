package com.aklbeti.account.dto;

public record AddressResponse(
        String street,

        int buildNo,

        CityResponse city
) {
}
