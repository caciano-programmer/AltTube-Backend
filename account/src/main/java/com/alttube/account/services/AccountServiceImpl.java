package com.alttube.account.services;

import com.alttube.account.models.AccountExtrasModel;
import com.alttube.account.models.AccountModel;
import com.alttube.account.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final SecurityService securityService;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, SecurityService securityService) {
        this.accountRepository = accountRepository;
        this.securityService = securityService;
    }

    @Override
    public String login(String email, String pass) {
        AccountModel account = accountRepository.findByEmail(email);
        securityService.passwordMatch(pass, account.getPassword());
        return null;
    }

    @Override
    public boolean authenticate(String token) {
        return false;
    }

    @Override
    public void update(String token, AccountExtrasModel accountExtrasModel) {}

    @Override
    public void create(AccountModel accountModel) {
        String encodedPass = securityService.hashPassword(accountModel.getPassword());
        accountModel.setPassword(encodedPass);
        accountRepository.save(accountModel);
    }

}
