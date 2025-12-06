package com.example.jar.model.repository;

import com.example.jar.model.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 게시글 데이터 접근을 위한 리포지토리 인터페이스입니다.
 * Spring Data JPA를 사용하여 기본적인 CRUD 및 페이징 기능을 제공합니다.
 */
@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    
    /**
     * 제목 또는 내용에 키워드가 포함된 게시글을 페이지 단위로 조회합니다.
     */
    Page<Board> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content, Pageable pageable);
    
    /**
     * 제목에 키워드가 포함된 게시글을 페이지 단위로 조회합니다.
     */
    Page<Board> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    /**
     * 조회수를 1 증가시킵니다. (JPQL 직접 쿼리)
     * 
     * @param id 게시글 ID
     * @return 영향을 받은 행의 수
     */
    @Modifying
    @Query("update Board b set b.viewCount = b.viewCount + 1 where b.id = :id")
    int incrementViewCount(@Param("id") Long id);

    /**
     * 좋아요 수를 1 증가시킵니다. (JPQL 직접 쿼리)
     * 
     * @param id 게시글 ID
     * @return 영향을 받은 행의 수
     */
    @Modifying
    @Query("update Board b set b.likeCount = b.likeCount + 1 where b.id = :id")
    int incrementLikeCount(@Param("id") Long id);
}
