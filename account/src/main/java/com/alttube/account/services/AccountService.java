package com.alttube.account.services;


import com.alttube.account.models.AccountExtrasModel;
import com.alttube.account.models.AccountModel;

public interface AccountService {

    String login(String email, String password);

    boolean authenticate(String jwt);

    void update(String token, AccountExtrasModel accountExtrasModel);

    void create(AccountModel accountModel);
}
