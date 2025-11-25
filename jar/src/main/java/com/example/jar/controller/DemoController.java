package com.example.jar.controller;

import com.example.jar.model.domain.Board;
import com.example.jar.model.domain.TestDB;
import com.example.jar.model.service.BlogService;
import com.example.jar.model.service.BoardService;
import com.example.jar.model.service.TestService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DemoController {

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    private final TestService testService;
    private final BlogService blogService;
    private final BoardService boardService;

    @GetMapping("/hello1004")
    public String hello(Model model) {
        model.addAttribute("data", "Hello, welcome!");
        return "hello";
    }

    @GetMapping("/about_detailed")
    public String aboutDetailed() {
        return "about_detailed";
    }

    @GetMapping("/")
    public String index(Model model) {
        // Use Board data for main page so newly created posts from /article_list (Board-based) appear here
        List<Board> articles = boardService.findAll();
        model.addAttribute("articles", articles);
        return "index";
    }

    @GetMapping("/test1")
    public String thymeleafTest1(Model model) {
        model.addAttribute("data1", "<h2>Hello</h2>");
        model.addAttribute("data2", "Sample inner value");
        model.addAttribute("link", "01");
        model.addAttribute("name", "Guest");
        model.addAttribute("para1", "001");
        model.addAttribute("para2", "002");
        return "thymeleaf_test1";
    }

    @GetMapping("/testdb")
    public String getAllTestDBs(Model model) {
        TestDB test = testService.findByName("Guest");
        model.addAttribute("data4", test);
        logger.info("테스트 데이터 조회: {}", test);
        return "testdb";
    }
}

