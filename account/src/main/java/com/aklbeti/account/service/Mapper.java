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

    public Profile toProfile(ProfileRequest request) {
        return Profile.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .phoneNumber(request.phoneNumber())
                .address(toAddress(request.address()))
                .build();
    }

    public Address toAddress(AddressRequest request) {
        return Address.builder()
                .street(request.street())
                .buildNo(request.buildNo())
                .city(null)
                .build();
    }

    public AccountResponse toAccountResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getEmailAddress(),
                toProfileResponse(account.getProfile())
        );
    }

    public ProfileResponse toProfileResponse(Profile profile) {
        return new ProfileResponse(
                profile.getFirstName(),
                profile.getLastName(),
                profile.getPhoneNumber(),
                toAddressResponse(profile.getAddress())
        );
    }

    public AddressResponse toAddressResponse(Address address) {
        return new AddressResponse(
                address.getStreet(),
                address.getBuildNo(),
                toCityResponse(address.getCity())
        );
    }

    public CityResponse toCityResponse(City city) {
        return new CityResponse(
                city.getName()
        );
    }
}
