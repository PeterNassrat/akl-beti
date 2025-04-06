package com.aklbeti.account.service;

import com.aklbeti.account.entity.Account;
import com.aklbeti.account.entity.Address;
import com.aklbeti.account.exception.AccountCreationException;
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
            throw new AccountCreationException("The account's email address already exists!");
        }

        // ensure that the ids are all zeros
        newAccount.setId(0);
        newAccount.getProfile().setId(0);
        for(Address address : newAccount.getProfile().getAddresses()) {
            address.setId(0);
        }
        accountRepository.save(newAccount);
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

    @Transactional
    public void delete(long id) {
        Account accountInDB = accountRepository.findById(id)
                .orElseThrow(() -> new AccountCreationException("Account does not exist!"));
        accountRepository.delete(accountInDB);
    }

    public Account findById(long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountCreationException("Account does not exist!"));
    }
     */
}
