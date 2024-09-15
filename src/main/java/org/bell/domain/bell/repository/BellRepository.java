package org.bell.domain.bell.repository;

import org.bell.domain.bell.entity.Bell;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BellRepository extends JpaRepository<Bell, Long> {
    Page<Bell> findAllByOrderByCreatedAtDesc(Pageable pageable);


    //조회수 탑4
    @Query(value = "SELECT b FROM Bell b ORDER BY b.hit DESC")
    List<Bell> findTop4ByOrderByHitDesc(Pageable pageable);

    //핫키워드 10
    @Query("SELECT b.keyword, COUNT(b.id) as viewCount " +
            "FROM Bell b " +
            "WHERE b.createdAt >= :startTime " +
            "GROUP BY b.keyword " +
            "ORDER BY viewCount DESC")
    List<Object[]> findTopKeywords(@Param("startTime") LocalDateTime startTime, Pageable pageable);


    //검색
    @Query("SELECT b FROM Bell b WHERE b.keyword LIKE %:keyword%")
    List<Bell> findByKeywordContaining(@Param("keyword") String keyword);
}