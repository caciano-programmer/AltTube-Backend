package com.alttube.account.services;

import com.alttube.account.models.AccountExtrasModel;
import com.alttube.account.models.AccountModel;

public interface AccountService {

    String login(String email, String password);

    boolean authenticate(String tokenHeader, String tokenCookie, String jwt);

    void update(AccountExtrasModel model, String email);

    void create(AccountModel accountModel);
}
