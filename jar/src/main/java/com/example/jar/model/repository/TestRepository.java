package com.example.jar.model.repository;

import com.example.jar.model.domain.TestDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<TestDB, Long> {
    // 'findByName' 메서드를 선언하면, Spring Data JPA가 메서드 이름을 분석하여
    // "SELECT * FROM testdb WHERE name = ?" 쿼리를 자동으로 생성하고 실행합니다.
    TestDB findByName(String name);
}
