package com.alttube.account.services;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public interface CookieService {

    void deleteCookie(HttpServletResponse response);

    Cookie getCookie(String name, String payload);

    HashMap<String, String> cookieValue(Cookie[] cookies);
}
