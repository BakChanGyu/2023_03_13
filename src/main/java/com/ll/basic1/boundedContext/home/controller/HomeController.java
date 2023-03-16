package com.ll.basic1.boundedContext.home.controller;

import com.ll.basic1.Person;
import com.ll.basic1.boundedContext.member.entitiy.Member;
import com.ll.basic1.boundedContext.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.*;

@Controller
public class HomeController {
    private int count;
    public final List<Person> people;

    // 필드주입
    private MemberService memberService;


    public HomeController(MemberService memberService) {
        count = -1;
        people = new ArrayList<>();
        this.memberService = memberService;
    }

    @GetMapping("/home/main")
    @ResponseBody
    public String showMain() {
        return "안녕하세요!";
    }

    @GetMapping("/home/main2")
    @ResponseBody
    public String showMain2() {
        return "반갑습니다.";
    }
    @GetMapping("/home/main3")
    @ResponseBody
    public String showMain3() {
        return "즐거웠습니다.";
    }

    @GetMapping("/home/increase")
    @ResponseBody
    public int showIncrese() {
        return ++count;
    }

    @GetMapping("/home/plus")
    @ResponseBody
    public int showPlus(@RequestParam(defaultValue = "0")int a, int b) {
        return a + b;
    }

    @GetMapping("/home/addPerson")
    @ResponseBody
    public String addPeople(@RequestParam String name, int age) {
        Person p = new Person(name, age);
        people.add(p);
        return "%d번 사람이 추가되었습니다.".formatted(p.getId());
    }

    @GetMapping("/home/removePerson")
    @ResponseBody
    public String removePeople(@RequestParam int id) {
        // 조건에 맞는걸 찾았고 삭제까지 되었다면 true. 아니면 false
        boolean removed = people.removeIf(e -> e.getId() == id);
        if (removed) {
            return "%d번 사람이 삭제되었습니다.".formatted(id);
        }

        return "%d번 사람은 존재하지 않습니다.".formatted(id);
    }

    @GetMapping("/home/modifyPerson")
    @ResponseBody
    public String modifyPeople(@RequestParam int id, String name, int age) {

        Person findPeople = people.stream().
                filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
        if (findPeople == null) {
            return "%d번 사람은 존재하지 않습니다.".formatted(id);
        }
        findPeople.setName(name);
        findPeople.setAge(age);
        return "%d번 사람이 수정되었습니다.".formatted(id);
    }

    @GetMapping("/home/cookie/increase")
    @ResponseBody
    public int showCookieIncrese(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int countInCookie = 0;

        if (req.getCookies() != null) {
            countInCookie = Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("count"))
                    .map(cookie -> cookie.getValue())
                    .mapToInt(Integer::parseInt)
                    .findFirst()
                    .orElse(0);
        }

        int newCountInCookie = countInCookie + 1;

        // 고객이 가져온 count 쿠폰값에 1을 더한 쿠폰을 만들어서 고객에게 보낸다.
        // 쉽게 말하면 count 쿠폰의 값을 1 증가시킨다.
        resp.addCookie(new Cookie("count",  newCountInCookie + ""));

        // 응답 본문
        return newCountInCookie;
    }

    @GetMapping("/home/user1")
    @ResponseBody
    public Member showUser1() {
        return memberService.findByUsername("user1");
    }

    @GetMapping("/home/reqAndResp")
    @ResponseBody
    public void showReqAndResp(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int age = Integer.parseInt(req.getParameter("age"));
        resp.getWriter().append("Hello, you are %d years old".formatted(age));
    }

    @GetMapping("/home/people")
    @ResponseBody
    public List<Person> showPeople() {
        return people;
    }
}
