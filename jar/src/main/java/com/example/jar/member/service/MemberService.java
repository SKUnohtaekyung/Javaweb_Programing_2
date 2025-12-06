package com.example.jar.member.service;

import com.example.jar.member.dto.AddMemberRequest;
import com.example.jar.model.domain.Member;
import com.example.jar.model.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 회원 관련 비즈니스 로직을 처리하는 서비스입니다.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 새로운 회원을 등록합니다.
     * 이메일 중복 확인 후 비밀번호를 암호화하여 저장합니다.
     * 
     * @param request 회원가입 요청 DTO
     * @return 저장된 회원 엔티티
     * @throws IllegalStateException 이미 존재하는 이메일일 경우
     */
    public Member saveMember(AddMemberRequest request) {
        validateDuplicateMember(request.getEmail());
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Member member = Member.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encodedPassword)
                .age(request.getAge())
                .mobile(request.getMobile())
                .address(request.getAddress())
                .build();
        return memberRepository.save(member);
    }

    /**
     * 로그인 시 이메일과 비밀번호를 검증합니다.
     * 
     * @param email       사용자 이메일
     * @param rawPassword 입력받은 비밀번호 (평문)
     * @return 로그인 성공한 회원 엔티티
     * @throws IllegalArgumentException 이메일이 없거나 비밀번호가 일치하지 않을 경우
     */
    public Member loginCheck(String email, String rawPassword) {
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            throw new IllegalArgumentException("등록되지 않은 이메일입니다.");
        }
        if (!passwordEncoder.matches(rawPassword, member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return member;
    }

    /**
     * 중복된 이메일이 있는지 확인합니다.
     * 
     * @param email 확인할 이메일
     * @throws IllegalStateException 이미 존재하는 이메일일 경우
     */
    private void validateDuplicateMember(String email) {
        Member findMember = memberRepository.findByEmail(email);
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 이메일입니다.");
        }
    }
}
