package com.ll.basic1.boundedContext.member.repository;

import com.ll.basic1.boundedContext.member.entitiy.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberRepository {
    private List<Member> members;

    public MemberRepository() {
        members = new ArrayList<>();
        members.add(new Member("user1", "1234"));
        members.add(new Member("giving", "12349"));
    }
    public void save(String username, String password) {
        List<Member> members = new ArrayList<>();
        Member member = new Member(username, password);
        members.add(member);
    }
    public Member findByUsername(String username) {
        return members.stream()
                .filter(m -> m.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public Member findById(long id) {
        return members.stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
