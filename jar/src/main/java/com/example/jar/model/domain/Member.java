package com.example.jar.model.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * 회원 정보를 저장하는 엔티티 클래스입니다.
 */
@Getter
@Entity
@Table(name = "member", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member {

    /**
     * 회원 고유 ID (Primary Key)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    /**
     * 회원 이름
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 회원 이메일 (로그인 ID로 사용됨, 중복 불가)
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * 비밀번호 (암호화되어 저장됨)
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * 회원 나이
     */
    @Column(name = "age", nullable = false)
    private Integer age;

    /**
     * 전화번호
     */
    @Column(name = "mobile", nullable = false)
    private String mobile;

    /**
     * 주소
     */
    @Column(name = "address", nullable = false)
    private String address;
}
