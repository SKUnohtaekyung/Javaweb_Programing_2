package com.example.jar.model.repository;

import com.example.jar.model.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 회원 데이터 접근을 위한 리포지토리 인터페이스입니다.
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
    /**
     * 이메일로 회원을 조회합니다.
     * 
     * @param email 조회할 회원 이메일
     * @return 조회된 회원 엔티티, 없으면 null
     */
    Member findByEmail(String email);
}
