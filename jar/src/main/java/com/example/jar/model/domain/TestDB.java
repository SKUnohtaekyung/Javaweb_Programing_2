package com.example.jar.model.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 테스트용 DB 테이블과 매핑되는 엔티티 클래스입니다.
 */
@Entity
@Table(name = "testdb")
@Getter
@Setter
@NoArgsConstructor
public class TestDB {

    /**
     * 기본 키 (Primary Key)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 이름 필드
     */
    @Column(nullable = true)
    private String name;
}