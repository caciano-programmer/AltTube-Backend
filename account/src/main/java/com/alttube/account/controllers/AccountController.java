package com.alttube.account.controllers;

import com.alttube.account.models.AccountExtrasModel;
import com.alttube.account.models.AccountModel;
import com.alttube.account.services.AccountService;
import com.alttube.account.services.CookieService;
import com.alttube.account.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Optional;

@RestController
public class AccountController {

    private final AccountService accountService;
    private final SecurityService securityService;
    private final CookieService cookieService;

    @Autowired
    public AccountController(AccountService accountService, SecurityService securityService, CookieService cookieService) {
        this.cookieService = cookieService;
        this.accountService = accountService;
        this.securityService = securityService;
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void login(@Valid @RequestHeader("Authorization") String credentials, HttpServletResponse response) {
        HashMap<String, String> loginInfo = securityService.getCredentialsFromHeader(credentials);
        String email = loginInfo.get("email");
        String password = loginInfo.get("password");
        accountService.login(email, password);
        addCookieAndToken(response, email);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/logout",
            method = RequestMethod.GET)
    public void logout(@Valid HttpServletResponse response){
        cookieService.deleteCookie(response);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/update_account",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void update(@Valid AccountExtrasModel extrasModel, @RequestHeader("token") String token, HttpServletRequest request) {
        HashMap<String, String> map = cookieService.cookieValue(request.getCookies());
        Optional<AccountModel> accountModel = accountService.authenticate(token, map.get("token"), map.get("jwt"));
        accountService.update(accountModel.get(), extrasModel);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/create_account",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void create(@Valid AccountModel accountModel, @RequestHeader("Authorization") String credentials, HttpServletResponse response) {
        HashMap<String, String> loginInfo = securityService.getCredentialsFromHeader(credentials);
        String email = loginInfo.get("email");
        String password = loginInfo.get("password");
        accountModel.setEmail(email);
        accountModel.setPassword(password);
        accountService.create(accountModel);
        addCookieAndToken(response, email);
    }

    private void addCookieAndToken(HttpServletResponse response, String email) {
        String uuid = securityService.randomTokenGenerator();
        String jwt = securityService.sendJwt(email);
        response.addCookie(cookieService.getCookie("jwt", jwt));
        response.addCookie(cookieService.getCookie("token", uuid));
        response.addHeader("token", uuid);
    }
}
