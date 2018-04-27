package com.alttube.account.services;

import com.alttube.account.models.AccountExtrasModel;
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
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Value("${Account.SecretKey}")
    private String secret;
    private final AccountRepository accountRepository;
    private final SecurityService securityService;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, SecurityService securityService) {
        this.accountRepository = accountRepository;
        this.securityService = securityService;
    }

    @Override
    public String login(String email, String pass) {
        AccountModel account = accountRepository.findByEmail(email);
        securityService.passwordMatch(pass, account.getPassword());
        return null;
    }

    @Override
    public Optional<AccountModel> authenticate(String tokenHeader, String tokenCookie, String jwt) {
        String jwtClaim = null;

        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .withIssuer("Alt-Tube")
                    .acceptLeeway(3600)
                    .build();
            DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
            Map<String, Claim> claims = decodedJWT.getClaims();
            Claim claim = claims.get("email");
            jwtClaim = claim.asString();
        } catch (UnsupportedEncodingException | JWTVerificationException x) { x.printStackTrace(); }

        if(!tokenHeader.equals(tokenCookie))
            return Optional.empty();
        return Optional.of(accountRepository.findByEmail(jwtClaim));
    }

    @Override
    public void update(AccountModel model, AccountExtrasModel extrasModel) {
        if(model.getAccountExtras() == null) accountRepository.save(model.addExtras(extrasModel));
        else accountRepository.save(model.setExtras(extrasModel));
    }

    @Override
    public void create(AccountModel accountModel) {
        String encodedPass = securityService.hashPassword(accountModel.getPassword());
        accountModel.setPassword(encodedPass);
        accountRepository.save(accountModel);
    }

}
