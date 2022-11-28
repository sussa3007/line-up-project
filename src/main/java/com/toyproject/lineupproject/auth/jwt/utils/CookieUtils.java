package com.toyproject.lineupproject.auth.jwt.utils;

import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.exception.GeneralException;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.util.Arrays;

@Component
public class CookieUtils {

    public Cookie createCookie(String cookieName, String value, int maxAge) {
        String newVelue = value;
        if (value.equals("/error")) {
            newVelue = "/events";
        }
        Cookie cookie = new Cookie(cookieName, newVelue);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        return cookie;
    }

    public Cookie findRedirectCookie(Cookie[] cookies) {
        if (cookies == null) throw new GeneralException(ErrorCode.NOT_FOUND_COOKIE);
        Cookie findCookie = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(JwtProperties.REDIRECTION_URI))
                .findFirst().orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND_COOKIE));
        if (findCookie.getValue().equals("/error")) {
            findCookie.setValue("/events");
        }
        return findCookie;
    }

    public Cookie findNullSafeRedirectCookie(Cookie[] cookies) {
        if (cookies == null) throw new GeneralException(ErrorCode.NOT_FOUND_COOKIE);
        Cookie findCookie = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(JwtProperties.REDIRECTION_URI))
                .findFirst().orElse(new Cookie(JwtProperties.REDIRECTION_URI, ""));
        if (findCookie.getValue().equals("/error")) {
            findCookie.setValue("/events");
        }
        return findCookie;
    }

    public Cookie findRefreshTokenCookie(Cookie[] cookies) {
        if (cookies == null) throw new GeneralException(ErrorCode.NOT_FOUND_COOKIE);
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(JwtProperties.COOKIE_NAME_REFRESH_TOKEN))
                .findFirst()
                .orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND_COOKIE));
    }

    public Cookie findAccessTokenCookie(Cookie[] cookies) {
        if (cookies == null) throw new GeneralException(ErrorCode.NOT_FOUND_COOKIE);
        Cookie findCookie = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(JwtProperties.COOKIE_NAME_ACCESS_TOKEN))
                .findFirst()
                .orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND_COOKIE));
        String jws = findCookie.getValue().replace("Bearer", "");
        findCookie.setValue(jws);
        return findCookie;
    }
}
