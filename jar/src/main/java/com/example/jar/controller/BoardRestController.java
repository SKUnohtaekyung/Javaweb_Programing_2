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

/**
 * 게시글 데이터 처리를 위한 REST API 컨트롤러입니다.
 * 비동기 요청(AJAX 등)에 대한 응답을 JSON 형태로 반환합니다.
 */
@RequiredArgsConstructor
@RestController
public class BoardRestController {

    private final BoardService boardService;

    /**
     * 새로운 게시글을 작성합니다.
     * 로그인한 사용자만 작성할 수 있으며, 세션에서 작성자 정보를 가져옵니다.
     * 
     * @param request 게시글 작성 요청 데이터 (제목, 내용 등)
     * @param session 현재 사용자 세션 객체
     * @return 생성된 게시글 정보와 201 Created 상태 코드
     */
    @PostMapping("/api/boards")
    public ResponseEntity<Board> addBoard(@ModelAttribute AddBoardRequest request, HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null || email.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        request.setUser(email); // 작성자 정보를 현재 로그인한 사용자 이메일로 설정
        Board saved = boardService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * 특정 게시글의 정보를 조회합니다.
     * 
     * @param id 조회할 게시글 ID
     * @return 게시글 정보와 200 OK 상태 코드
     */
    @GetMapping("/api/boards/{id}")
    public ResponseEntity<Board> getBoard(@PathVariable long id) {
        return ResponseEntity.ok(boardService.findById(id));
    }

    /**
     * 게시글을 조회하고 조회수를 증가시킵니다.
     * 주로 모달(Modal) 창에서 상세 내용을 띄울 때 사용됩니다.
     * 
     * @param id 조회할 게시글 ID
     * @return 업데이트된 게시글 정보와 200 OK 상태 코드
     */
    @GetMapping("/api/boards/{id}/view")
    public ResponseEntity<Board> viewBoard(@PathVariable long id) {
        return ResponseEntity.ok(boardService.incrementCount(id));
    }

    /**
     * 특정 게시글을 삭제합니다.
     * 작성자 본인만 삭제할 수 있습니다.
     * 
     * @param id      삭제할 게시글 ID
     * @param session 현재 사용자 세션 객체
     * @return 성공 시 200 OK, 권한 없음 시 401/403 상태 코드
     */
    @DeleteMapping("/api/boards/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable long id, HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null || email.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Board board = boardService.findById(id);
        // 작성자 본인 확인
        if (!email.equals(board.getAuthor())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        boardService.delete(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 게시글에 좋아요를 누릅니다.
     * 좋아요 수가 1 증가합니다.
     * 
     * @param id 좋아요를 누를 게시글 ID
     * @return 업데이트된 게시글 정보와 200 OK 상태 코드
     */
    @PostMapping("/api/boards/{id}/like")
    public ResponseEntity<Board> likeBoard(@PathVariable long id) {
        Board updated = boardService.incrementLike(id);
        return ResponseEntity.ok(updated);
    }
}
