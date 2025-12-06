package com.example.jar.model.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 문의 정보를 저장하는 엔티티 클래스입니다.
 */
@Getter
@Entity
@Table(name = "contact_message")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ContactMessage {

    /**
     * 문의 고유 ID (Primary Key)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    /**
     * 작성자 이름
     */
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * 작성자 이메일
     */
    @Column(name = "email", nullable = false, length = 150)
    private String email;

    /**
     * 문의 제목
     */
    @Column(name = "subject", nullable = false, length = 200)
    private String subject;

    /**
     * 문의 내용
     */
    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;

    /**
     * 첫 번째 첨부파일 원본 이름
     */
    @Column(name = "attachment_original_name")
    private String attachmentOriginalName;

    /**
     * 첫 번째 첨부파일 저장된 이름
     */
    @Column(name = "attachment_saved_name")
    private String attachmentSavedName;

    /**
     * 두 번째 첨부파일 원본 이름
     */
    @Column(name = "attachment_original_name2")
    private String attachmentOriginalName2;

    /**
     * 두 번째 첨부파일 저장된 이름
     */
    @Column(name = "attachment_saved_name2")
    private String attachmentSavedName2;

    /**
     * 작성 일시
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * 엔티티가 영속화되기 전에 실행됩니다.
     * 작성 일시가 없으면 현재 시간으로 설정합니다.
     */
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
