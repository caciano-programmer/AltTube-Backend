package com.alttube.account.services;

import com.alttube.account.models.AccountModel;
import com.alttube.account.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void login(String email, String password) {}

    @Override
    public boolean authenticate(String jwt) {
        return false;
    }

    @Override
    public void update(AccountModel accountController) {}

    @Override
    public void create(AccountModel accountModel) {
        accountRepository.save(accountModel);
    }

}
