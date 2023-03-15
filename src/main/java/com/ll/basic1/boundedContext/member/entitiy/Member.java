package com.ll.basic1.boundedContext.member.entitiy;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class Member {
    private static long lastId;
    private long id;
    private String username;
    private final String password;

    static {
        lastId = 0;
    }

    public Member(String username, String password) {
        this(++lastId, username, password);
    }
}
