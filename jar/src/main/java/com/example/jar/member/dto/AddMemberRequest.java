package com.example.jar.member.dto;

import com.example.jar.model.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 회원가입 요청을 전달하는 DTO 클래스입니다.
 * 입력값 유효성 검증 조건을 포함합니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddMemberRequest {

    /**
     * 이름 (필수, 비을 수 없음)
     */
    @NotBlank
    private String name;

    /**
     * 이메일 (필수, 이메일 형식)
     */
    @NotBlank
    @Email
    private String email;

    /**
     * 비밀번호 (필수, 영문/숫자/특수문자 포함 8자 이상)
     */
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,}$")
    private String password;

    /**
     * 나이 (필수, 19세 이상 90세 이하)
     */
    @NotNull
    @Min(19)
    @Max(90)
    private Integer age;

    /**
     * 전화번호 (필수)
     */
    @NotBlank
    private String mobile;

    /**
     * 주소 (필수)
     */
    @NotBlank
    private String address;

    /**
     * DTO를 Member 엔티티로 변환합니다.
     * 
     * @return Member 엔티티
     */
    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .age(age)
                .mobile(mobile)
                .address(address)
                .build();
    }
}
