package com.ll.basic1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    private int count;
    private List<People> people;

    public HomeController() {
        count = -1;
        people = new ArrayList<>();
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
        People p = new People(name, age);
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

        People findPeople = people.stream().
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

    @GetMapping("/home/people")
    @ResponseBody
    public List<People> showPeople() {
        return people;
    }
}
