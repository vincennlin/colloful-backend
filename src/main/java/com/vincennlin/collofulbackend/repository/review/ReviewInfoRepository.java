package com.vincennlin.collofulbackend.repository.review;

import com.vincennlin.collofulbackend.entity.review.ReviewInfo;
import com.vincennlin.collofulbackend.entity.word.Word;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface ReviewInfoRepository extends JpaRepository<ReviewInfo, Long> {

    @Query("""
        SELECT w FROM Word w
        JOIN w.reviewInfo r
        WHERE w.user.id = :userId
        AND r.nextReview < :nextReviewThreshold
        ORDER BY r.nextReview ASC
    """)
        Page<Word> findDueWordsByUserIdBeforeTime(Long userId, LocalDateTime nextReviewThreshold, Pageable pageable);
}