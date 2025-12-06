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

/**
 * 게시글 관련 비즈니스 로직을 처리하는 서비스입니다.
 */
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    /**
     * 모든 게시글을 리스트로 조회합니다.
     * (하위 호환성을 위해 유지됨)
     * 
     * @return 게시글 리스트
     */
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    /**
     * 페이징 처리된 모든 게시글 목록을 조회합니다.
     * 
     * @param pageable 페이징 정보 (페이지 번호, 크기, 정렬 등)
     * @return 페이징된 게시글 목록
     */
    public Page<Board> findAll(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    /**
     * 키워드로 게시글을 검색합니다. (제목 또는 내용)
     * 현재 데이터베이스 호환성 문제로 제목 포함 여부만 검색하도록 구현되어 있습니다.
     * 
     * @param keyword  검색할 키워드
     * @param pageable 페이징 정보
     * @return 검색된 게시글 목록
     */
    public Page<Board> searchByKeyword(String keyword, Pageable pageable) {
        // H2 데이터베이스 CLOB 타입에 대한 LIKE 검색 이슈로 인해 제목 검색만 수행합니다.
        return boardRepository.findByTitleContainingIgnoreCase(keyword, pageable);
    }

    /**
     * 새로운 게시글을 저장합니다.
     * 작성자 익명 처리 및 초기값 설정을 수행합니다.
     * 
     * @param request 게시글 작성 요청 DTO
     * @return 저장된 게시글 엔티티
     */
    public Board save(AddBoardRequest request) {
        String title = request.getTitle();
        String content = request.getContent();
        // 작성자가 없으면 "anonymous"로 처리
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

    /**
     * ID로 게시글을 단건 조회합니다.
     * 
     * @param id 조회할 게시글 ID
     * @return 조회된 게시글 엔티티
     * @throws IllegalArgumentException 게시글이 존재하지 않을 경우
     */
    public Board findById(long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    /**
     * 게시글을 삭제합니다.
     * 
     * @param id 삭제할 게시글 ID
     */
    public void delete(long id) {
        boardRepository.deleteById(id);
    }

    /**
     * 게시글을 수정합니다.
     * 트랜잭션 내에서 엔티티를 조회하고 변경 감지(Dirty Checking)를 통해 업데이트합니다.
     * 
     * @param id      수정할 게시글 ID
     * @param request 수정할 내용이 담긴 요청 DTO
     * @return 수정된 게시글 엔티티
     * @throws IllegalArgumentException 게시글이 존재하지 않을 경우
     */
    @Transactional
    public Board update(long id, UpdateBoardRequest request) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        board.update(request.getTitle(), request.getContent());

        return board;
    }

    /**
     * 게시글 조회수를 증가시킵니다.
     * 
     * @param id 조회수를 증가시킬 게시글 ID
     * @return 업데이트된 게시글 엔티티
     * @throws IllegalArgumentException 게시글이 존재하지 않을 경우
     */
    @Transactional
    public Board incrementCount(long id) {
        boardRepository.incrementViewCount(id);
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    /**
     * 게시글 좋아요 수를 증가시킵니다.
     * 
     * @param id 좋아요 수를 증가시킬 게시글 ID
     * @return 업데이트된 게시글 엔티티
     * @throws IllegalArgumentException 게시글이 존재하지 않을 경우
     */
    @Transactional
    public Board incrementLike(long id) {
        boardRepository.incrementLikeCount(id);
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }
}
