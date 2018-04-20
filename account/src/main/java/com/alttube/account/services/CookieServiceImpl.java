package com.alttube.account.services;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Service
public class CookieServiceImpl implements CookieService {

    @Override
    public void deleteCookie(HttpServletResponse response) {
        Cookie tokenCookie = new Cookie("token", null);
        Cookie jwtCookie = new Cookie("jwt", null);
        Cookie emailCookie = new Cookie("email", null);
        tokenCookie.setMaxAge(0);
        jwtCookie.setMaxAge(0);
        emailCookie.setMaxAge(0);
        response.addCookie(tokenCookie);
        response.addCookie(jwtCookie);
        response.addCookie(emailCookie);
    }

    @Override
    public Cookie getCookie(String name, String payload) {
        Cookie cookie = new Cookie(name, payload);
        cookie.setMaxAge(3600);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }

    @Override
    public HashMap<String, String> cookieValue(Cookie[] cookies) {
        HashMap<String, String> map = new HashMap<>();
        for(Cookie cookie: cookies) {
            if(cookie.getName().equals("token"))
                map.put("token", cookie.getValue());
            else if(cookie.getName().equals("jwt"))
                map.put("jwt", cookie.getValue());
        }
        return map;
    }
}
