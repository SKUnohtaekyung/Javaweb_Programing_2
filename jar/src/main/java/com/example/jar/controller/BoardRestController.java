package com.example.jar.controller;

import com.example.jar.model.domain.Board;
import com.example.jar.model.service.AddBoardRequest;
import com.example.jar.model.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class BoardRestController {

    private final BoardService boardService;

    @PostMapping("/api/boards")
    public ResponseEntity<Board> addBoard(@ModelAttribute AddBoardRequest request) {
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
    public ResponseEntity<Void> deleteBoard(@PathVariable long id) {
        boardService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/boards/{id}/like")
    public ResponseEntity<Board> likeBoard(@PathVariable long id) {
        Board updated = boardService.incrementLike(id);
        return ResponseEntity.ok(updated);
    }
}
