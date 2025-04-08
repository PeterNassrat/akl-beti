package com.aklbeti.account.service;

import com.aklbeti.account.entity.City;
import com.aklbeti.account.exception.CityNotFoundException;
import com.aklbeti.account.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public boolean doesExist(String name) {
        return cityRepository.findByName(name).isPresent();
    }

    public City findByName(String name) {
        return cityRepository.findByName(name).orElseThrow(
                () -> new CityNotFoundException("City does not exist!")
        );
    }

    public List<City> findAll() {
        return cityRepository.findAll();
    }
}
