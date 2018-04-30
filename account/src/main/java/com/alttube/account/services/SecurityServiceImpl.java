package com.alttube.account.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Value("${Account.SecretKey}")
    private String secretKey;
    private final ExceptionService exceptionService;

    @Autowired
    public SecurityServiceImpl(ExceptionService exceptionService) { this.exceptionService = exceptionService; }

    @Override
    public HashMap<String, String> getCredentialsFromHeader(String token) {
        String[] split = token.split(" ");
        String type = split[0];
        String decodedCredentials = decode(split[1]);
        

        String[] cred = decodedCredentials.split(":", 2);
        HashMap<String,String> credentials = new HashMap<>(2);

        boolean incorrectFormat = Pattern.compile("(.*)[:](.*)[:](.*)").matcher(decodedCredentials).matches() || !cred[0].contains("@");
        if(!type.equals("Basic") || incorrectFormat) exceptionService.throwInvalidCredentialsFormatException();

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
        }catch (UnsupportedEncodingException x) { x.printStackTrace(); }

        return jwt;
    }

    @Override
    public String randomTokenGenerator() {
        return UUID.randomUUID().toString();
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
