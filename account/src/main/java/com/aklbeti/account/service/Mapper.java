package com.aklbeti.account.service;

import com.aklbeti.account.dto.*;
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

    private Profile toProfile(ProfileRequest request) {
        return Profile.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .addresses(request.addresses().stream().map(this::toAddress).toList())
                .build();
    }

    private Address toAddress(AddressRequest request) {
        return Address.builder()
                .street(request.street())
                .buildNo(request.buildNo())
                .city(toCity(request.city()))
                .build();
    }

    private City toCity(CityRequest request) {
        return City.builder()
                .name(request.name())
                .build();
    }

    public AccountResponse toAccountResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getEmailAddress(),
                toProfileResponse(account.getProfile())
        );
    }

    private ProfileResponse toProfileResponse(Profile profile) {
        return new ProfileResponse(
                profile.getFirstName(),
                profile.getLastName(),
                profile.getAddresses().stream().map(this::toAddressResponse).toList()
        );
    }

    private AddressResponse toAddressResponse(Address address) {
        return new AddressResponse(
                address.getStreet(),
                address.getBuildNo(),
                toCityResponse(address.getCity())
        );
    }

    private CityResponse toCityResponse(City city) {
        return new CityResponse(
                city.getName()
        );
    }
}
