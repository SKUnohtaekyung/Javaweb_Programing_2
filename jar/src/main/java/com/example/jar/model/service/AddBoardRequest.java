package com.example.jar.model.service;

import com.example.jar.model.domain.Board;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddBoardRequest {
    private String title;
    private String content;
    private String user; // mapped to Board.author

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .author(user == null || user.isBlank() ? "anonymous" : user)
                .build();
    }
}
