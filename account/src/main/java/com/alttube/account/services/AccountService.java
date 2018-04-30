package com.alttube.account.services;

import com.alttube.account.models.AccountExtrasModel;
import com.alttube.account.models.AccountModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface AccountService {

    void login(String email, String password);

    void update(AccountModel accountModel, AccountExtrasModel accountExtrasModel);

    void create(AccountModel accountModel);

    void saveImage(MultipartFile file, AccountExtrasModel extrasModel);
}
