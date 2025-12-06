package com.example.jar.controller;

import com.example.jar.member.dto.AddMemberRequest;
import com.example.jar.member.service.MemberService;
import com.example.jar.model.domain.Member;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 회원 관련 기능을 처리하는 컨트롤러입니다.
 * 회원가입, 로그인, 로그아웃 기능을 제공합니다.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입 폼을 보여줍니다.
     * 
     * @param model 뷰에 데이터를 전달하기 위한 객체
     * @return 회원가입 폼 뷰 이름 (join_new)
     */
    @GetMapping("/join_new")
    public String joinForm(Model model) {
        if (!model.containsAttribute("addMemberRequest")) {
            model.addAttribute("addMemberRequest", new AddMemberRequest());
        }
        return "join_new";
    }

    /**
     * 회원가입을 처리합니다.
     * 
     * @param form          회원가입 요청 데이터
     * @param bindingResult 입력값 검증 결과
     * @param model         뷰에 데이터를 전달하기 위한 객체
     * @return 가입 성공 시 완료 페이지, 실패 시 가입 폼
     */
    @PostMapping("/api/members")
    public String saveMember(@ModelAttribute("addMemberRequest") @Valid AddMemberRequest form,
                             BindingResult bindingResult,
                             Model model) {
        // 입력값 검증 실패 시 다시 폼으로 이동
        if (bindingResult.hasErrors()) {
            return "join_new";
        }
        try {
            memberService.saveMember(form);
        } catch (IllegalStateException e) {
            // 중복 이메일 등 예외 발생 시 에러 메시지 전달
            model.addAttribute("errorMessage", e.getMessage());
            return "join_new";
        }
        return "join_end";
    }

    /**
     * 로그인 폼을 보여줍니다.
     * 
     * @return 로그인 폼 뷰 이름 (login)
     */
    @GetMapping("/member_login")
    public String loginForm() {
        return "login";
    }

    /**
     * 로그인을 처리합니다.
     * 
     * @param email    사용자 이메일
     * @param password 사용자 비밀번호
     * @param model    뷰에 데이터를 전달하기 위한 객체
     * @param session  현재 사용자 세션 객체
     * @return 성공 시 메인 페이지 리다이렉트, 실패 시 로그인 페이지
     */
    @PostMapping("/api/login_check")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model,
                        HttpSession session) {
        try {
            // 이메일과 비밀번호 확인
            Member member = memberService.loginCheck(email, password);
            // 로그인 성공 시 세션에 회원 정보 저장
            session.setAttribute("memberId", member.getId());
            session.setAttribute("email", member.getEmail());
            session.setAttribute("name", member.getName());
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            // 로그인 실패 시 에러 메시지 전달
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("email", email);
            return "login";
        }
    }

    /**
     * 로그아웃을 처리합니다.
     * 세션을 무효화하고 JSESSIONID 쿠키를 삭제합니다.
     * 
     * @param session  현재 사용자 세션 객체
     * @param response HTTP 응답 객체 (쿠키 삭제용)
     * @return 메인 페이지 리다이렉트
     */
    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response) {
        session.invalidate();
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
