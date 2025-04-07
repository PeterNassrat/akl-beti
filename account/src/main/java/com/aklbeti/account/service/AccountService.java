package com.aklbeti.account.service;

import com.aklbeti.account.entity.Account;
import com.aklbeti.account.entity.Address;
import com.aklbeti.account.exception.AccountNotFoundException;
import com.aklbeti.account.exception.DuplicateAccountException;
import com.aklbeti.account.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void create(Account newAccount) {
        Optional<Account> AccountInDB = accountRepository.findByEmailAddress(newAccount.getEmailAddress());
        if(AccountInDB.isPresent()) {
            throw new DuplicateAccountException("Account already exists!");
        }

        // ensure that the ids are all zeros
        newAccount.setId(0);
        newAccount.getProfile().setId(0);
        for(Address address : newAccount.getProfile().getAddresses()) {
            address.setId(0);
        }
        accountRepository.save(newAccount);
    }

    public Account findByEmailAddressWithAddressesAndCities(String emailAddress) {
        return accountRepository.findByEmailAddress_(emailAddress)
                .orElseThrow(() -> new AccountNotFoundException("Account does not exist!"));
    }

    public Account findById(long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account does not exist!"));
    }

    public boolean isAccountExist(String emailAddress) {
        return accountRepository.findByEmailAddress(emailAddress).isPresent();
    }

    @Transactional
    public void delete(long id) {
        accountRepository.deleteById(id);
    }

    // public Account findByIdWithAddressesAndCities(long id) {
    //    return accountRepository.findByIdWithAddressesAndCities(id)
    //            .orElseThrow(() -> new AccountNotFoundException("Account does not exist!"));
    // }

    @Transactional
    public void save(Account account) {
        accountRepository.save(account);
    }

    /*
    @Transactional
    public Account update(Account updatedAccount) {
        Account accountInDB = accountRepository.findById(updatedAccount.getId())
                .orElseThrow(() -> new AccountCreationException("Account does not exist!"));

        // update the content of the accountDB
        accountInDB.setActive(updatedAccount.isActive());
        accountInDB.setProfile(updatedAccount.getProfile());
        accountInDB.setPassword(updatedAccount.getPassword());
        accountInDB.setEmailAddress(updatedAccount.getEmailAddress());

        return accountRepository.save(accountInDB);
    }
     */
}
