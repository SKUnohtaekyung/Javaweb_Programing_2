package com.example.jar.model.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false, length = 255)
    private String title = "";

    @Lob
    @Column(name = "content", nullable = false)
    private String content = "";

    @Column(name = "author", nullable = false, length = 100)
    private String author = "";

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "view_count", nullable = false)
    private int viewCount = 0;

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

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) this.createdAt = LocalDateTime.now();
    }
}
