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
        String uuid = securityService.randomTokenGenerator();
        String jwt = securityService.sendJwt(email);

        accountService.login(email, password);
        response.addCookie(cookieService.getCookie("jwt", jwt));
        response.addCookie(cookieService.getCookie("token", uuid));
        response.addCookie(cookieService.getCookie("email", email));
        response.addHeader("token", uuid);
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
    public void update(@Valid AccountExtrasModel model, @RequestHeader("token") String token, HttpServletRequest request) {
        HashMap<String, String> map = cookieService.cookieValue(request.getCookies());
        accountService.authenticate(token, map.get("token"), map.get("jwt"));
        String email = map.get("email");
        accountService.update(model, email);
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
