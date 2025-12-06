package com.example.jar.controller;

import com.example.jar.model.domain.Board;
import com.example.jar.model.domain.TestDB;
import com.example.jar.model.service.BoardService;
import com.example.jar.model.service.TestService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 데모 및 테스트 목적의 컨트롤러입니다.
 * 인덱스 페이지와 일부 테스트 페이지를 제공합니다.
 */
@Controller
@RequiredArgsConstructor
public class DemoController {

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    private final TestService testService;
    private final BoardService boardService;

    /**
     * 간단한 인사말 페이지를 반환합니다.
     */
    @GetMapping("/hello1004")
    public String hello(Model model) {
        model.addAttribute("data", "Hello, welcome!");
        return "hello";
    }

    /**
     * 상세 소개 페이지를 반환합니다.
     */
    @GetMapping("/about_detailed")
    public String aboutDetailed() {
        return "about_detailed";
    }

    /**
     * 애플리케이션의 메인 페이지(인덱스)를 반환합니다.
     * 게시글 목록을 불러와 함께 표시합니다.
     */
    @GetMapping("/")
    public String index(Model model) {
        // 메인 페이지에 게시글 목록 표시 (최신순 등을 고려할 수 있음)
        List<Board> articles = boardService.findAll();
        model.addAttribute("articles", articles);
        return "index";
    }

    /**
     * 타임리프(Thymeleaf) 기능 테스트용 페이지입니다.
     */
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

    /**
     * 테스트 DB 연결 확인을 위한 페이지입니다.
     */
    @GetMapping("/testdb")
    public String getAllTestDBs(Model model) {
        TestDB test = testService.findByName("Guest");
        model.addAttribute("data4", test);
        logger.info("테스트 데이터 조회: {}", test);
        return "testdb";
    }
}
