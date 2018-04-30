package com.alttube.account.services;

import com.alttube.account.models.AccountModel;

import java.util.HashMap;
import java.util.Optional;

public interface SecurityService {

    HashMap<String, String> getCredentialsFromHeader(String token);

    String sendJwt(String email);

    String randomTokenGenerator();

    String hashPassword(String password);

    boolean passwordMatch(String password, String encodedPassword);

    Optional<AccountModel> authenticate(String tokenHeader, String tokenCookie, String jwt);
}
