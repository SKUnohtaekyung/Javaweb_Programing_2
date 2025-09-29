package com.example.jar.controller;

import com.example.jar.model.domain.TestDB;
import com.example.jar.model.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DemoController {

    private final TestService testService;

    @GetMapping("/hello1004")
    public String hello(Model model) {
        model.addAttribute("data", "반갑습니다.");
        return "hello";
    }

    @GetMapping("/about_detailed")
    public String aboutDetailed() {
        return "about_detailed";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/test1")
    public String thymeleaf_test1(Model model) {
        model.addAttribute("data1", "<h2> 반갑습니다</h2>");
        model.addAttribute("data2", "태그의속성값");
        model.addAttribute("link", "01");
        model.addAttribute("name", "홍길동");
        model.addAttribute("para1", "001");
        model.addAttribute("para2", "002");
        return "thymeleaf_test1";
    }

    @GetMapping("/testdb")
    public String getAllTestDBs(Model model) {
        TestDB test = testService.findByName("홍길동");
        model.addAttribute("data4", test);
        System.out.println("데이터출력디버그: " + test);
        return "testdb";
    }
}
