package com.aklbeti.account.controller;

import com.aklbeti.account.dto.CitiesResponse;
import com.aklbeti.account.dto.Response;
import com.aklbeti.account.service.CityService;
import com.aklbeti.account.service.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cities")
public class CityController {

    private final CityService cityService;
    private final Mapper mapper;

    public CityController(CityService cityService,
                          Mapper mapper) {
        this.cityService = cityService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<CitiesResponse> getAll() {
        return ResponseEntity.ok(new CitiesResponse(
                new Response(true, "Cities was sent successfully."),
                cityService.findAll().stream().map(mapper::toCityResponse).toList()
        ));
    }
}
