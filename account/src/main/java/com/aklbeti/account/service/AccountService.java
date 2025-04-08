package com.aklbeti.account.service;

import com.aklbeti.account.entity.Account;
import com.aklbeti.account.exception.AccountNotFoundException;
import com.aklbeti.account.exception.DuplicateAccountException;
import com.aklbeti.account.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void create(Account account) {
        if(doesExist(account.getEmailAddress())) {
            throw new DuplicateAccountException("Account already exists!");
        }

        // ensure that the ids are all zeros
        account.setId(0);
        account.getProfile().setId(0);
        account.getProfile().getAddress().setId(0);
        accountRepository.save(account);
    }

    @Transactional
    public void update(Account account) {
        if(doesExist(account.getId())) {
            accountRepository.save(account);
            return;
        }
        throw new AccountNotFoundException("Account does not exist!");
    }

    @Transactional
    public void deleteById(long id) {
        if(doesExist(id)) {
            accountRepository.deleteById(id);
            return;
        }
        throw new AccountNotFoundException("Account does not exist!");
    }

    public Account findByEmailAddress(String emailAddress) {
        return accountRepository.findByEmailAddress(emailAddress)
                .orElseThrow(() -> new AccountNotFoundException("Account does not exist!"));
    }

    public Account findById(long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account does not exist!"));
    }

    public boolean doesExist(String emailAddress) {
        return accountRepository.findByEmailAddress(emailAddress).isPresent();
    }

    public boolean doesExist(long id) {
        return accountRepository.findById(id).isPresent();
    }
}
