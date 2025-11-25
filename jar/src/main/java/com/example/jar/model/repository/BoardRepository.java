package com.example.jar.model.repository;

import com.example.jar.model.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content, Pageable pageable);
    Page<Board> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    @Modifying
    @Query("update Board b set b.viewCount = b.viewCount + 1 where b.id = :id")
    int incrementViewCount(@Param("id") Long id);

    @Modifying
    @Query("update Board b set b.likeCount = b.likeCount + 1 where b.id = :id")
    int incrementLikeCount(@Param("id") Long id);
}
