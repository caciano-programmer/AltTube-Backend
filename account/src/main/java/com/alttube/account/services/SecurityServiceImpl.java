package com.alttube.account.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Value("${Account.SecretKey}")
    private String secretKey;

    @Override
    public HashMap<String, String> getCredentialsFromHeader(String token) {
        String[] split = token.split(" ");
        String type = split[0];
        String decodedCredentials = decode(split[1]);

        String[] cred = decodedCredentials.split(":", 2);
        HashMap<String,String> credentials = new HashMap<>(2);
        credentials.put("email", cred[0]);
        credentials.put("password", cred[1]);
        return credentials;
    }

    @Override
    public String sendJwt(String email) {
        String jwt = null;

        try{
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            jwt = JWT.create()
                    .withClaim("email", email)
                    .withIssuedAt(new Date())
                    .withIssuer("Alt-Tube")
                    .sign(algorithm);
        }catch (UnsupportedEncodingException x) {}

        return jwt;
    }

    @Override
    public String hashPassword(String password) { return new BCryptPasswordEncoder().encode(password); }

    @Override
    public boolean passwordMatch(String password, String encoded) { return new BCryptPasswordEncoder().matches(password, encoded); }

    private String decode(String credentials) {
        byte[] bytes = Base64.getDecoder().decode(credentials);
        return new String(bytes);
    }
}
