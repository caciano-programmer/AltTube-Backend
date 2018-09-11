package com.alttube.account.services;

import com.alttube.account.models.AccountExtrasModel;
import com.alttube.account.models.AccountModel;
import org.springframework.web.multipart.MultipartFile;

public interface AccountService {

    String login(String email, String password);

    void update(AccountModel accountModel, AccountExtrasModel accountExtrasModel);

    void create(AccountModel accountModel);

    void saveImage(MultipartFile file, AccountExtrasModel extrasModel);
}
