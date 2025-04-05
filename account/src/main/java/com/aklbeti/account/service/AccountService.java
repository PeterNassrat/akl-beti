package com.aklbeti.account.service;

import com.aklbeti.account.entity.Account;
import com.aklbeti.account.exception.AccountException;
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
    public Account create(Account newAccount) {
        Optional<Account> optionalAccountInDB = accountRepository.findByEmail(newAccount.getEmailAddress());
        if(optionalAccountInDB.isPresent()) {
            throw new AccountException("Account already exists!");
        }
        newAccount.setId(0);
        return accountRepository.save(newAccount);
    }

    @Transactional
    public Account update(Account updatedAccount) {
        Account accountInDB = accountRepository.findById(updatedAccount.getId())
                .orElseThrow(() -> new AccountException("Account does not exist!"));

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
                .orElseThrow(() -> new AccountException("Account does not exist!"));
        accountRepository.delete(accountInDB);
    }

    public Account findById(long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountException("Account does not exist!"));
    }
}
