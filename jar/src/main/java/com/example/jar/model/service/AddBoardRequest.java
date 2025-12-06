package com.example.jar.model.service;

import com.example.jar.model.domain.Board;
import lombok.*;

/**
 * 게시글 작성 요청을 전달하는 DTO 클래스입니다.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddBoardRequest {
    /**
     * 게시글 제목
     */
    private String title;
    
    /**
     * 게시글 내용
     */
    private String content;
    
    /**
     * 작성자 (Board.author 필드에 매핑됨)
     */
    private String user;

    /**
     * DTO를 Board 엔티티로 변환합니다.
     * 작성자가 없으면 "anonymous"로 설정합니다.
     * 
     * @return Board 엔티티
     */
    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .author(user == null || user.isBlank() ? "anonymous" : user)
                .build();
    }
}
