package com.example.jar.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 전역 예외 처리를 담당하는 핸들러입니다.
 * 모든 컨트롤러에서 발생하는 특정 예외를 잡아 공통된 에러 페이지를 보여줍니다.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 메소드 인자 타입 불일치 예외를 처리합니다.
     * 예: 게시글 ID(Long)에 문자열이 들어온 경우 등
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatch(MethodArgumentTypeMismatchException ex, Model model) {
        model.addAttribute("errorMessage", "잘못된 요청입니다. 게시글 ID는 숫자여야 합니다.");
        return "error/400";
    }

    /**
     * 잘못된 상태(IllegalStateException) 또는 잘못된 인자(IllegalArgumentException) 예외를 처리합니다.
     * 비즈니스 로직 검증 실패 시 주로 발생합니다.
     */
    @ExceptionHandler({IllegalStateException.class, IllegalArgumentException.class})
    public String handleBadRequest(RuntimeException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/400";
    }
}
