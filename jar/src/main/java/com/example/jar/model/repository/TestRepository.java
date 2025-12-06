package com.example.jar.model.repository;

import com.example.jar.model.domain.TestDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 테스트 데이터 접근을 위한 리포지토리입니다.
 */
@Repository
public interface TestRepository extends JpaRepository<TestDB, Long> {
    
    /**
     * 이름으로 데이터를 조회합니다.
     * Spring Data JPA가 메서드 이름을 분석하여 쿼리를 자동 생성합니다.
     */
    TestDB findByName(String name);
}
