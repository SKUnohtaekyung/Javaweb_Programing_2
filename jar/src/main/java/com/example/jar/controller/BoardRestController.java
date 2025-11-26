package com.example.jar.controller;

import com.example.jar.model.domain.Board;
import com.example.jar.model.service.AddBoardRequest;
import com.example.jar.model.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import jakarta.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class BoardRestController {

    private final BoardService boardService;

    @PostMapping("/api/boards")
    public ResponseEntity<Board> addBoard(@ModelAttribute AddBoardRequest request, HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null || email.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        request.setUser(email); // 작성자 입력을 세션 정보로 덮어쓰기
        Board saved = boardService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/api/boards/{id}")
    public ResponseEntity<Board> getBoard(@PathVariable long id) {
        return ResponseEntity.ok(boardService.findById(id));
    }

    // For modal view: increment view count and return updated data
    @GetMapping("/api/boards/{id}/view")
    public ResponseEntity<Board> viewBoard(@PathVariable long id) {
        return ResponseEntity.ok(boardService.incrementCount(id));
    }

    @DeleteMapping("/api/boards/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable long id, HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null || email.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Board board = boardService.findById(id);
        if (!email.equals(board.getAuthor())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        boardService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/boards/{id}/like")
    public ResponseEntity<Board> likeBoard(@PathVariable long id) {
        Board updated = boardService.incrementLike(id);
        return ResponseEntity.ok(updated);
    }
}
