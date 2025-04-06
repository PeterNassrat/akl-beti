package com.aklbeti.account.service;

import com.aklbeti.account.dto.AddressRequest;
import com.aklbeti.account.dto.CityRequest;
import com.aklbeti.account.dto.ProfileRequest;
import com.aklbeti.account.dto.RegistrationRequest;
import com.aklbeti.account.entity.Account;
import com.aklbeti.account.entity.Address;
import com.aklbeti.account.entity.City;
import com.aklbeti.account.entity.Profile;
import org.springframework.stereotype.Service;

@Service
public class Mapper {

    public Account toAccount(RegistrationRequest request) {
        return Account.builder()
                .emailAddress(request.emailAddress())
                .password(request.password())
                .profile(toProfile(request.profile()))
                .build();
    }

    public Profile toProfile(ProfileRequest request) {
        return Profile.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .addresses(request.addresses().stream().map(this::toAddress).toList())
                .build();
    }

    public Address toAddress(AddressRequest request) {
        return Address.builder()
                .street(request.street())
                .buildNo(request.buildNo())
                .city(toCity(request.city()))
                .build();
    }

    public City toCity(CityRequest request) {
        return City.builder()
                .name(request.name())
                .build();
    }


}
