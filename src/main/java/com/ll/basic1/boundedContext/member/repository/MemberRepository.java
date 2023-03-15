package com.ll.basic1.boundedContext.member.repository;

import com.ll.basic1.boundedContext.member.entitiy.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberRepository {
    private List<Member> members;
    private Member member;

    public MemberRepository() {
        members = new ArrayList<>();
        members.add(new Member("user1", "1234"));
    }
    public void save(String username, String password) {
        List<Member> members = new ArrayList<>();
        Member member = new Member(username, password);
        members.add(member);
    }

    public List<Member> list() {
        return members;
    }

    public Member findByUsername(String username) {
        return members.stream()
                .filter(m -> m.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}
