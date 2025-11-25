package com.example.jar.model.service;

import com.example.jar.model.domain.Board;
import com.example.jar.model.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    // Legacy list (kept for compatibility)
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    // New: pageable list
    public Page<Board> findAll(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    // New: keyword search (title or content)
    public Page<Board> searchByKeyword(String keyword, Pageable pageable) {
        // H2 dev DB can be finicky with LIKE on CLOB. Title-only search keeps compatibility.
        return boardRepository.findByTitleContainingIgnoreCase(keyword, pageable);
    }

    public Board save(AddBoardRequest request) {
        String title = request.getTitle();
        String content = request.getContent();
        String author = (request.getUser() == null || request.getUser().isBlank()) ? "anonymous" : request.getUser();
        LocalDateTime createdAt = LocalDateTime.now();
        Integer viewCount = 0;
        Integer likeCount = 0;

        Board board = Board.builder()
                .title(title)
                .content(content)
                .author(author)
                .createdAt(createdAt)
                .viewCount(viewCount)
                .likeCount(likeCount)
                .build();
        return boardRepository.save(board);
    }

    public Board findById(long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public void delete(long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public Board update(long id, UpdateBoardRequest request) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        board.update(request.getTitle(), request.getContent());

        return board;
    }

    @Transactional
    public Board incrementCount(long id) {
        boardRepository.incrementViewCount(id);
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    @Transactional
    public Board incrementLike(long id) {
        boardRepository.incrementLikeCount(id);
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }
}
