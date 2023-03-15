package com.ll.basic1.boundedContext.member.entitiy;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@AllArgsConstructor
@Data
public class Rq {
    private HttpServletRequest req;
    private HttpServletResponse resp;

    public static void setCookie(String name, Object value) {
        resp.addCookie(new Cookie(name, value + ""));
    }

    public String getCookie(String cookieName, String def) {
        return Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(def);
    }

    public long getCookieAsLong(String cookieName, int def) {
        return Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .map(Cookie::getValue)
                .mapToLong(Long::parseLong)
                .findFirst()
                .orElse(def);
    }
    public void removeCookie(String cookieName) {
        if (req.getCookies() != null) {
            Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals(cookieName))
                    .forEach(cookie -> {
                        cookie.setMaxAge(0);
                        resp.addCookie(cookie);
                    });
        }
    }
}
