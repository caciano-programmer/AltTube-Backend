package com.alttube.account.controllers;

import com.alttube.account.models.AccountModel;
import com.alttube.account.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public AccountController login(@Valid AccountController credentials) {
        return null;
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
