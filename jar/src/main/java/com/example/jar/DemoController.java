package com.example.jar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 이 클래스가 웹 요청을 처리하는 컨트롤러임을 나타냅니다.
public class DemoController { // 클래스 이름은 대문자로 시작하는 것이 관례입니다.

    @GetMapping("/hello1004") // HTTP GET 요청을 "/hello" 경로와 매핑합니다.
    public String hello(Model model) {
        // "data"라는 이름으로 "반갑습니다."라는 값을 모델에 추가합니다.
        // 이 데이터는 뷰(HTML)에서 사용될 수 있습니다.
        model.addAttribute("data", "반갑습니다.");
        
        // "hello"라는 이름의 뷰(보통 hello.html)를 찾아 렌더링하도록 반환합니다.
        return "hello";
    }
}