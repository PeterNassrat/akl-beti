package com.aklbeti.account.dto;

import java.util.List;

public record CitiesResponse(

        Response response,

        List<CityResponse> cities
) {
}
