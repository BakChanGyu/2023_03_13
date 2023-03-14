package com.ll.basic1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class People {
    private static int lastId;
    private int id;
    private String name;
    private int age;

    static {
        lastId = 0;
    }
    public People(String name, int age) {
        this(++lastId, name, age);
    }
}
