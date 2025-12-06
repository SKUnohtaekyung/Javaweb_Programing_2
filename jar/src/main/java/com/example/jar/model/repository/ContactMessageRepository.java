package com.example.jar.model.repository;

import com.example.jar.model.domain.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 문의 내역 데이터 접근을 위한 리포지토리 인터페이스입니다.
 */
@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
}
