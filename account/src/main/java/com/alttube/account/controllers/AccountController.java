package com.alttube.account.controllers;

import com.alttube.account.models.AccountModel;
import com.alttube.account.services.AccountService;
import com.alttube.account.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;

@RestController
public class AccountController {

    private final AccountService accountService;
    private final SecurityService securityService;

    @Autowired
    public AccountController(AccountService accountService, SecurityService securityService) {
        this.accountService = accountService;
        this.securityService = securityService;
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void login(@Valid @RequestHeader("Authorization") String credentials, HttpServletResponse response) {
        HashMap<String, String> loginInfo = securityService.getCredentialsFromHeader(credentials);
        String email = loginInfo.get("email");
        String password = loginInfo.get("password");
        accountService.login(email, password);

        Cookie cookie = new Cookie("jwt", securityService.sendJwt(email));
        cookie.setMaxAge(3600);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/update_account",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public AccountController update(@Valid String ID) {
        return null;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/create_account",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void create(@Valid AccountModel accountModel) {
        accountService.create(accountModel);
    }
}
