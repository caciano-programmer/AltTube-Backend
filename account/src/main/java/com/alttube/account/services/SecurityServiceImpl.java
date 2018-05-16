package com.alttube.account.services;

import com.alttube.account.models.AccountModel;
import com.alttube.account.repositories.AccountRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
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
    private final AccountRepository accountRepository;

    @Autowired
    public SecurityServiceImpl(ExceptionService exceptionService, AccountRepository accountRepository) {
        this.exceptionService = exceptionService;
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<AccountModel> authenticate(String tokenHeader, String tokenCookie, String jwt) {
        Optional<String> email = authenticateHelper(tokenHeader, tokenCookie, jwt);
        return email.isPresent() ? Optional.of(accountRepository.findByEmail(email.get())) : Optional.empty();
    }

    @Override
    public boolean isAuthenticated(String tokenHeader, String tokenCookie, String jwt) {
        Optional<String> email = authenticateHelper(tokenHeader, tokenCookie, jwt);
        return email.isPresent() ? accountRepository.existsByEmail(email.get()) : false;
    }

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

    private Optional<String> authenticateHelper(String tokenHeader, String tokenCookie, String jwt) {

        if(!tokenHeader.equals(tokenCookie)) exceptionService.throwInvalidCredentialsFormatException();
        Optional<String> jwtClaim = Optional.empty();

        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .withIssuer("Alt-Tube")
                    .acceptLeeway(3600)
                    .build();
            DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
            Map<String, Claim> claims = decodedJWT.getClaims();
            Claim claim = claims.get("email");
            jwtClaim = Optional.of(claim.asString());
        } catch(UnsupportedEncodingException | JWTVerificationException x) { x.printStackTrace(); }

        return jwtClaim;
    }

    private String decode(String credentials) {
        byte[] bytes = Base64.getDecoder().decode(credentials);
        return new String(bytes);
    }
}
