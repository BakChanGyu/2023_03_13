package com.ll.basic1.base.rq;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;

@AllArgsConstructor
@Data
public class Rq {
    private final HttpServletRequest req;
    private final HttpServletResponse resp;

    public void setCookie(String name, long value) {
        setCookie(name, value + "");
        resp.addCookie(new Cookie(name, value + ""));
    }
    public void setCookie(String name, String value) {
        setCookie(name, value);
        resp.addCookie(new Cookie(name, value + ""));
    }

    public String getCookie(String cookieName, String def) {
        if (req.getCookies() == null) return def;

        // 해당 이름의 쿠키가 있으면 그 쿠키의 값, 업으면 defaultValue값 리턴.
        return Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(def);
    }

    public long getCookieAsLong(String cookieName, long def) {
        // 이름이 이거인거 줘봐. 값 있으면 값. 없으면 null.
        String value = getCookie(cookieName, null);

        if (value == null) {
            return def;
        }

        try {
            return Long.parseLong(value);
        }
        catch (NumberFormatException e) {
            return def;
        }
    }

    public boolean removeCookie(String cookieName) {
        if (req.getCookies() != null) {
            Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals(cookieName))
                    .forEach(cookie -> {
                        cookie.setMaxAge(0);
                        resp.addCookie(cookie);
                    });

            return Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals(cookieName))
                    .count() > 0;

        }

        return false;
    }
}
