package com.aklbeti.account.controller;

import com.aklbeti.account.dto.*;
import com.aklbeti.account.entity.Account;
import com.aklbeti.account.entity.Address;
import com.aklbeti.account.exception.AccountCreationException;
import com.aklbeti.account.service.Mapper;
import com.aklbeti.account.service.AccountService;
import com.aklbeti.account.service.CityService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;
    private final CityService cityService;
    private final Mapper mapper;

    public AccountController(AccountService accountService,
                             CityService cityService,
                             Mapper mapper) {
        this.accountService = accountService;
        this.cityService = cityService;
        this.mapper = mapper;
    }

    @PostMapping("/register")
    public ResponseEntity<Response> register(
            @Valid @RequestBody RegistrationRequest request) {

        // city validation
        List<String> cities = request.profile().addresses().stream()
                .map(AddressRequest::city).map(CityRequest::name).toList();

        for(int i = 0; i < cities.size(); i++) {
            if (!cityService.isCityExist(cities.get(i))) {
                throw new RegistrationException(
                        String.format("The city \"%s\" in the %dth address is not valid!", cities.get(i), i + 1));
            }
        }

        // create new account
        Account newAccount = mapper.toAccount(request);

        // put the city for addresses
        for(Address address : newAccount.getProfile().getAddresses()) {
            address.setCity(cityService.findByName(address.getCity().getName()));
        }

        accountService.create(newAccount);
        return ResponseEntity.ok(new RegistrationResponse(true, "Account created successfully."));
    }
}
