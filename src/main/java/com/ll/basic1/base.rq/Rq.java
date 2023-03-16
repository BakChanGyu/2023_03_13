package com.ll.basic1.base.rq;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Arrays;
import java.util.Enumeration;

@AllArgsConstructor
@RequestScope
@Component
public class Rq {
    private final HttpServletRequest req;
    private final HttpServletResponse resp;

    public void setCookie(String name, long value) {
//        setCookie(name, value + "");
        resp.addCookie(new Cookie(name, value + ""));
    }
    public void setCookie(String name, String value) {
//        setCookie(name, value);
        resp.addCookie(new Cookie(name, value));
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
            Cookie cookie = Arrays.stream(req.getCookies())
                    .filter(e -> e.getName().equals(cookieName))
                    .findFirst()
                    .orElse(null);

            if (cookie != null) {
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
            }

            return Arrays.stream(req.getCookies())
                    .filter(e -> e.getName().equals(cookieName))
                    .count() > 0;
        }
        return false;
    }

    public void setSession(String name, long value) {
        HttpSession session = req.getSession();
        session.setAttribute(name, value);
    }
    // 객체를 세션 or 쿠키에 저장하는건 좋지않다.

    private String getSessionStr(String name, String defaultValue) {
        try {
            String value = (String) req.getSession().getAttribute(name);
            if (value == null) return defaultValue;
            return value;
        } catch (Exception e) {
            return defaultValue;
        }
    }
    public long getSessionAsLong(String name, long defaultValue) {
        try {
            long value = (long) req.getSession().getAttribute(name);
            return value;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public boolean removeSession(String name) {
        HttpSession session = req.getSession();

        if (session.getAttribute(name) == null) return false;

        session.removeAttribute(name);
        return true;
    }

    public String getSessionDebugContents() {
        HttpSession session = req.getSession();
        StringBuilder sb = new StringBuilder("Session content:\n");

        Enumeration<String> attributeNames =
                session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            Object attributeValue = session.getAttribute(attributeName);
            sb.append(String.format("%s: %s\n", attributeName, attributeValue));
        }
        return sb.toString();
    }
}
