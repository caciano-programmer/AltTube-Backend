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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Optional;

@CrossOrigin(exposedHeaders = "token", allowCredentials = "true")
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
    @RequestMapping(value = "/account/login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public HashMap<String, String> login(@Valid @RequestHeader("Authorization") String credentials, HttpServletResponse response) {
        HashMap<String, String> loginInfo = securityService.getCredentialsFromHeader(credentials);
        String email = loginInfo.get("email");
        String password = loginInfo.get("password");
        String name = accountService.login(email, password);
        addCookieAndToken(response, email);
        HashMap<String, String> map = new HashMap<>();
        map.put("status", "successful");
        map.put("name", name);
        return map;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/account/logout",
            method = RequestMethod.GET)
    public void logout(@Valid HttpServletResponse response){
        cookieService.deleteCookie(response);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/account/update_account",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String update(@Valid AccountExtrasModel extrasModel, @RequestHeader("token") String token,
                       @RequestPart(required = false) MultipartFile file, HttpServletRequest request) {
        HashMap<String, String> map = cookieService.cookieValue(request.getCookies());
        Optional<AccountModel> accountModel = securityService.authenticate(token, map.get("token"), map.get("jwt"));
        if(file != null) accountService.saveImage(file, extrasModel);

        accountService.update(accountModel.get(), extrasModel);
        return "{\"status\": \"successful\"}";
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/account/profile",
    method = RequestMethod.GET)
    public HashMap<String, Object> getAccount(@RequestHeader("token") String token, HttpServletRequest request) {
        HashMap<String, String> map = cookieService.cookieValue(request.getCookies());
        Optional<AccountModel> accountModel = securityService.authenticate(token, map.get("token"), map.get("jwt"));
        AccountExtrasModel extras = accountModel.get().getAccountExtras();
        if(extras == null) return null;

        HashMap<String, Object> profile = new HashMap<>();
        if(extras.getAge() != null) profile.put("age", extras.getAge().toString());
        if(extras.getGender() != null) profile.put("gender", extras.getGender().toString());
        if(extras.getDescription() != null) profile.put("description", extras.getDescription());
        if(extras.getImageName() != null) profile.put("image", Base64.getEncoder().encode(setImage(extras)));
        return profile;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/account/create_account",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public HashMap<String, String> create(@Valid AccountModel accountModel, @RequestHeader("Authorization") String credentials, HttpServletResponse response) {
        HashMap<String, String> loginInfo = securityService.getCredentialsFromHeader(credentials);
        String email = loginInfo.get("email");
        String password = loginInfo.get("password");
        String name = accountModel.getName();
        accountModel.setEmail(email);
        accountModel.setPassword(password);
        accountService.create(accountModel);
        addCookieAndToken(response, email);
        HashMap<String, String> map = new HashMap<>();
        map.put("status", "successful");
        map.put("name", name);
        return map;
    }

    private void addCookieAndToken(HttpServletResponse response, String email) {
        String uuid = securityService.randomTokenGenerator();
        String jwt = securityService.sendJwt(email);
        response.addCookie(cookieService.getCookie("jwt", jwt));
        response.addCookie(cookieService.getCookie("token", uuid));
        response.addCookie(cookieService.getCookie("email", email));
        response.addHeader("token", uuid);
    }

    private byte[] setImage(AccountExtrasModel extras) {
        Path path = Paths.get(Paths.get("").toAbsolutePath().toString() + "/account-images/" + extras.getImageName());
        try { return Files.readAllBytes(path); }
        catch (IOException ex) {return null;}
    }
}
