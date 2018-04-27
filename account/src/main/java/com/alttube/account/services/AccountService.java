package com.alttube.account.services;

import com.alttube.account.models.AccountExtrasModel;
import com.alttube.account.models.AccountModel;
import java.util.Optional;

public interface AccountService {

    void login(String email, String password);

    Optional<AccountModel> authenticate(String tokenHeader, String tokenCookie, String jwt);

    void update(AccountModel accountModel, AccountExtrasModel accountExtrasModel);

    void create(AccountModel accountModel);
}
