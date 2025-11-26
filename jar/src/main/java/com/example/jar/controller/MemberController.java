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

@Controller
@RequiredArgsConstructor
@RequestMapping
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join_new")
    public String joinForm(Model model) {
        if (!model.containsAttribute("addMemberRequest")) {
            model.addAttribute("addMemberRequest", new AddMemberRequest());
        }
        return "join_new";
    }

    @PostMapping("/api/members")
    public String saveMember(@ModelAttribute("addMemberRequest") @Valid AddMemberRequest form,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            return "join_new";
        }
        try {
            memberService.saveMember(form);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "join_new";
        }
        return "join_end";
    }

    @GetMapping("/member_login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/api/login_check")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model,
                        HttpSession session) {
        try {
            Member member = memberService.loginCheck(email, password);
            session.setAttribute("memberId", member.getId());
            session.setAttribute("email", member.getEmail());
            session.setAttribute("name", member.getName());
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("email", email);
            return "login";
        }
    }

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
