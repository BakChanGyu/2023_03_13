package com.ll.basic1.boundedContext.member.controller;

import com.ll.basic1.base.RsData;
import com.ll.basic1.boundedContext.member.entitiy.Member;
import com.ll.basic1.base.rq.Rq;
import com.ll.basic1.boundedContext.member.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final Rq rq;

    @GetMapping("/member/login")
    @ResponseBody
    public String showlogin() {
        if (rq.isLogined()) {
            return """
                    <h1>이미 로그인 되었습니다.</h1>
                    """.stripIndent(); // 이거머임
        }

        return """
                <h1>로그인</h1>
                <form action="doLogin">
                <input type="text" placeholder="아이디" name="username">
                <input type="password" placeholder="비밀번호" name="password">
                <input type="submit" value="로그인">
                </form>
                """;
    }

    @GetMapping("/member/doLogin")
    @ResponseBody
    public RsData dologin(String username, String password) {

        if (username == null || username.trim().length() ==0 ) {
            return RsData.of("F-3", "username(을)을 입력해주세요.");
        }

        if (password == null || password.trim().length() ==0 ) {
            return RsData.of("F-4", "password(을)을 입력해주세요.");
        }

        RsData rsData = memberService.tryLogin(username, password);

        if (rsData.isSuccess()) {
            Member member = (Member) rsData.getData();
            rq.setSession("loginedMemberId", member.getId());
        }
        return rsData;
    }

    @GetMapping("/member/logout")
    @ResponseBody
    public RsData logout() {
        boolean cookieRemoved = rq.removeSession("loginedMemberId");

        if (cookieRemoved == false) {
            return RsData.of("S-2", "이미 로그아웃 상태입니다.");
        } else {
            return RsData.of("S-1", "로그아웃 되었습니다.");
        }
    }

    @GetMapping("/member/me")
    @ResponseBody
    public RsData showMe() {

        long loginedMemberId = rq.getSessionAsLong("loginedMemberId", 0);

        boolean isLogined = loginedMemberId > 0;

        if (isLogined == false) {
            return RsData.of("F-1", "로그인 후 이용해주세요.");
        }
        Member member = memberService.findById(loginedMemberId);
        return RsData.of("S-1", "당신의 username(은)는 %s 입니다.".formatted(member.getUsername()), member.getId());

    }

    // 디버깅용 함수
    @GetMapping("/member/session")
    @ResponseBody
    public String showSession() {
        return rq.getSessionDebugContents().replace("\n", "<br>");
    }

}
