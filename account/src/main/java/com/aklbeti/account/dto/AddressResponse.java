package com.aklbeti.account.dto;

public record AddressResponse(
        long id,

        String street,

        int buildNo,

        CityResponse city
) {
}
