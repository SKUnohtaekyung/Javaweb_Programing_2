package com.example.jar.model.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 게시글 수정 요청을 전달하는 DTO 클래스입니다.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateBoardRequest {
    /**
     * 수정할 제목
     */
    private String title;
    
    /**
     * 수정할 내용
     */
    private String content;
}

