package com.example.jar.model.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * TestDB 객체와 데이터베이스의 testdb 테이블을 매핑하는 Entity 클래스입니다.
 */
@Entity // 이 클래스가 JPA에 의해 관리되는 엔티티임을 나타냅니다.
@Table(name = "testdb") // 실제 데이터베이스의 'testdb' 테이블과 매핑됩니다.
@Getter
@Setter
@NoArgsConstructor // JPA는 기본 생성자를 필요로 합니다.
public class TestDB {

    /**
     * 기본 키(Primary Key) 필드입니다.
     * GenerationType.IDENTITY 전략은 데이터베이스의 AUTO_INCREMENT 기능을 사용합니다.
     */
    @Id // 이 필드가 테이블의 기본 키(PK)임을 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 값을 데이터베이스가 자동으로 생성하도록 합니다.
    private Long id;

    /**
     * 이름을 저장하는 필드입니다.
     * nullable = true는 이 컬럼에 null 값이 허용됨을 의미합니다.
     */
    @Column(nullable = true) // 테이블의 컬럼 설정을 명시합니다.
    private String name;
}