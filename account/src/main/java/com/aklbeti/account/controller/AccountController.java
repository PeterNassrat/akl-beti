package com.aklbeti.account.controller;

import com.aklbeti.account.dto.*;
import com.aklbeti.account.entity.Account;
import com.aklbeti.account.entity.Address;
import com.aklbeti.account.exception.*;
import com.aklbeti.account.service.Mapper;
import com.aklbeti.account.service.AccountService;
import com.aklbeti.account.service.CityService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;

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

        /*
        // city validation
        List<String> cities = request.profile().addresses().stream()
                .map(AddressRequest::city).map(CityRequest::name).toList();

        for(int i = 0; i < cities.size(); i++) {
            if (!cityService.isCityExist(cities.get(i))) {
                throw new RegistrationException(
                        String.format("The city \"%s\" in the %dth address is not valid!", cities.get(i), i + 1));
            }
        }
         */

        // map request to account entity
        Account account = mapper.toAccount(request);

        // put the city for addresses from database
        for(Address address : account.getProfile().getAddresses()) {
            address.setCity(cityService.findByName(address.getCity().getName()));
        }

        accountService.create(account);
        return ResponseEntity.ok(new Response(true, "Registered successfully."));
    }

    @GetMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        if(accountService.isAccountExist(request.emailAddress())) {
            Account account = accountService.findByEmailAddressWithAddressesAndCities(request.emailAddress());
            if(request.password().equals(account.getPassword())) {
                return ResponseEntity.ok(new LoginResponse(
                        new Response(true, "Logged in successfully."),
                        mapper.toAccountResponse(account)
                ));
            }
        }
        throw new LoginException("Invalid email or password!");
    }

    @DeleteMapping("/close/{id}")
    public ResponseEntity<Response> close(@Valid @RequestBody CloseRequest request, @Positive @PathVariable long id) {
        Account account = accountService.findById(id);
        if(request.password().equals(account.getPassword())) {
            accountService.deleteById(id);
            return ResponseEntity.ok(new Response(true, "Closed successfully."));
        }
        throw new CloseException("Invalid password!");
    }

    @PutMapping("/update/{id}/password")
    public ResponseEntity<Response> updatePassword(@Valid @RequestBody UpdatePasswordRequest request,
                                                   @Positive @PathVariable long id) {
        Account account = accountService.findById(id);
        if(request.oldPassword().equals(account.getPassword())) {
            account.setPassword(request.newPassword());
            accountService.update(account);
            return ResponseEntity.ok(new Response(true, "Password updated successfully."));
        }
        throw new UpdateException("Invalid password!");
    }

    @PutMapping("/update/{id}/profile")
    public ResponseEntity<Response> updateProfile(@Valid @RequestBody UpdateProfileRequest request,
                                                  @Positive @PathVariable long id) {
        Account account = accountService.findById(id);
        account.getProfile().setFirstName(request.firstName());
        account.getProfile().setLastName(request.lastName());
        accountService.update(account);
        return ResponseEntity.ok(new Response(true, "Profile updated successfully."));
    }

    @PutMapping("/update/{id}/update-address/{addressId}")
    public ResponseEntity<Response> updateAddress(@Valid @RequestBody AddressRequest request,
                                                  @Positive @PathVariable long id,
                                                  @Positive @PathVariable long addressId) {
        // get address
        Account account = accountService.findByIdWithAddressesAndCities(id);
        Address addressToUpdate = null;
        for(Address address : account.getProfile().getAddresses()) {
            if(address.getId() == addressId) {
                addressToUpdate = address;
                break;
            }
        }
        if(addressToUpdate == null) {
            throw new AddressUpdateException("Invalid address id!");
        }

        // update address
        addressToUpdate.setStreet(request.street());
        addressToUpdate.setBuildNo(request.buildNo());
        addressToUpdate.setCity(cityService.findByName(request.city().name()));

        // update account
        accountService.update(account);

        return ResponseEntity.ok(new Response(true, "Address updated successfully."));
    }

    @PutMapping("/update/{id}/delete-address/{addressId}")
    public ResponseEntity<Response> deleteAddress(@Positive @PathVariable long id,
                                                  @Positive @PathVariable long addressId) {
        Account account = accountService.findByIdWithAddressesAndCities(id);

        // find and delete the address with the addressId
        Iterator<Address> iterator = account.getProfile().getAddresses().iterator();
        while(iterator.hasNext()) {
            Address address = iterator.next();
            if(address.getId() == addressId) {
                if(account.getProfile().getAddresses().size() == 1) {
                    throw new AddressDeletionException("Account must have at least 1 address!");
                }
                iterator.remove();
                accountService.update(account);
                return ResponseEntity.ok(new Response(true, "Address deleted successfully."));
            }
        }

        throw new AddressDeletionException("Invalid address id!");
    }

    @PutMapping("/update/{id}/insert-address")
    public ResponseEntity<Response> insertAddress(@Valid @RequestBody AddressRequest request,
                                                  @Positive @PathVariable long id) {
        Account account = accountService.findByIdWithAddressesAndCities(id);
        account.getProfile().getAddresses().add(
                new Address(0, request.street(), request.buildNo(), cityService.findByName(request.city().name()))
        );
        accountService.update(account);
        return ResponseEntity.ok(new Response(true, "Address inserted successfully."));
    }

    @GetMapping("/get-info/{id}")
    public ResponseEntity<AccountInfoResponse> getInfo(@Positive @PathVariable long id) {
        Account account = accountService.findByIdWithAddressesAndCities(id);
        return ResponseEntity.ok(new AccountInfoResponse(
                new Response(true, "Information was sent successfully."),
                mapper.toAccountResponse(account)
        ));
    }

}
