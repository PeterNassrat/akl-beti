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

        // map request to account entity
        Account account = mapper.toAccount(request);

        // put the city for address from database
        account.getProfile().getAddress().setCity(
                cityService.findByName(request.profile().address().city().name())
        );

        accountService.create(account);
        return ResponseEntity.ok(new Response(true, "Registered successfully."));
    }

    @GetMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        if(accountService.doesExist(request.emailAddress())) {
            Account account = accountService.findByEmailAddress(request.emailAddress());
            if(request.password().equals(account.getPassword())) {
                return ResponseEntity.ok(new LoginResponse(
                        new Response(true, "Logged in successfully."),
                        mapper.toAccountResponse(account)
                ));
            }
        }
        throw new AccountLoginException("Invalid email or password!");
    }

    @GetMapping("/get-info/{id}")
    public ResponseEntity<AccountInfoResponse> getInfo(@Positive @PathVariable long id) {
        Account account = accountService.findById(id);
        return ResponseEntity.ok(new AccountInfoResponse(
                new Response(true, "Information was sent successfully."),
                mapper.toAccountResponse(account)
        ));
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
        throw new AccountUpdateException("Wrong password!");
    }

    @PutMapping("/update/{id}/profile")
    public ResponseEntity<Response> updateProfile(@Valid @RequestBody ProfileRequest request,
                                                  @Positive @PathVariable long id) {
        if(cityService.doesExist(request.address().city().name())) {
            Account account = accountService.findById(id);

            // update profile
            account.getProfile().setFirstName(request.firstName());
            account.getProfile().setLastName(request.lastName());
            account.getProfile().setPhoneNumber(request.phoneNumber());

            // update address
            Address address = account.getProfile().getAddress();
            address.setStreet(request.address().street());
            address.setBuildNo(request.address().buildNo());
            address.setCity(cityService.findByName(request.address().city().name()));

            accountService.update(account);
            return ResponseEntity.ok(new Response(true, "Profile updated successfully."));
        }
        throw new AccountUpdateException("Invalid city!");
    }

    @DeleteMapping("/close/{id}")
    public ResponseEntity<Response> close(@Valid @RequestBody CloseRequest request, @Positive @PathVariable long id) {
        Account account = accountService.findById(id);
        if(request.password().equals(account.getPassword())) {
            accountService.deleteById(id);
            return ResponseEntity.ok(new Response(true, "Closed successfully."));
        }
        throw new AccountCloseException("Wrong password!");
    }
}
