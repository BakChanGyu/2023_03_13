package com.ll.basic1.boundedContext.member.service;

import com.ll.basic1.base.RsData;
import com.ll.basic1.boundedContext.member.entitiy.Member;
import com.ll.basic1.boundedContext.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

// 아래 클래스는 Ioc 컨테이너에 의해 생사소멸이 관리된다.
@Service
@AllArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public RsData tryLogin(String username, String password) {
        Member member = memberRepository.findByUsername(username);

        if (member == null) {
            return RsData.of("F-2", "%s(은)는 존재하지 않는 회원입니다.".formatted(username));
        }
        if (!member.getPassword().equals(password)) {
            return RsData.of("F-1", "비밀번호가 일치하지 않습니다.");
        }
        else if (!member.getUsername().equals(username)) {
            return RsData.of("F-2", "%s(은)는 존재하지 않는 회원입니다.".formatted(username));
        }

        return RsData.of("S-1", "%s 님 환영합니다.".formatted(username));
    }
    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }
}
