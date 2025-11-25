package com.example.jar.member.service;

import com.example.jar.member.dto.AddMemberRequest;
import com.example.jar.model.domain.Member;
import com.example.jar.model.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

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

    private void validateDuplicateMember(String email) {
        Member findMember = memberRepository.findByEmail(email);
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 이메일입니다.");
        }
    }
}
