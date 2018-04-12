package com.alttube.account.services;


import com.alttube.account.models.AccountModel;

public interface AccountService {

    void login(String email, String password);

    boolean authenticate(String jwt);

    void update(AccountModel accountModel);

    void create(AccountModel accountModel);
}
