package com.alttube.account.services;

import java.util.HashMap;

public interface SecurityService {

    HashMap<String, String> getCredentialsFromHeader(String token);

    String sendJwt(String email);

    String randomTokenGenerator();

    String hashPassword(String password);

    boolean passwordMatch(String password, String encodedPassword);
}
