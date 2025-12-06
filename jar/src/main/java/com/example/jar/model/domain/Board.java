package com.example.jar.model.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 게시판 글 정보를 저장하는 엔티티 클래스입니다.
 */
@Getter
@Entity
@Table(name = "board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    /**
     * 게시글 고유 ID (Primary Key)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    /**
     * 게시글 제목
     */
    @Column(name = "title", nullable = false, length = 255)
    private String title = "";

    /**
     * 게시글 내용 (대용량 텍스트)
     */
    @Lob
    @Column(name = "content", nullable = false)
    private String content = "";

    /**
     * 작성자 (이메일 또는 식별자)
     */
    @Column(name = "author", nullable = false, length = 100)
    private String author = "";

    /**
     * 작성 일시
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * 조회수
     */
    @Column(name = "view_count", nullable = false)
    private int viewCount = 0;

    /**
     * 좋아요 수
     */
    @Column(name = "like_count", nullable = false)
    private int likeCount = 0;

    @Builder
    public Board(String title, String content, String author, LocalDateTime createdAt, Integer viewCount, Integer likeCount) {
        this.title = title;
        this.content = content;
        this.author = author != null ? author : "";
        this.createdAt = createdAt;
        if (viewCount != null) this.viewCount = viewCount;
        if (likeCount != null) this.likeCount = likeCount;
    }

    /**
     * 게시글 제목과 내용을 수정합니다.
     * 
     * @param title   새로운 제목
     * @param content 새로운 내용
     */
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    /**
     * 엔티티가 영속화되기 전에 실행됩니다.
     * 작성 일시가 없으면 현재 시간으로 설정합니다.
     */
    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) this.createdAt = LocalDateTime.now();
    }
}
