package com.vincennlin.collofulbackend.repository.review;

import com.vincennlin.collofulbackend.entity.review.ReviewInfo;
import com.vincennlin.collofulbackend.entity.word.Word;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewInfoRepository extends JpaRepository<ReviewInfo, Long> {

    @Query("SELECT w FROM Word w JOIN w.reviewInfo r WHERE w.user.id = :userId AND r.nextReview < CURRENT_TIMESTAMP ORDER BY r.nextReview ASC")
    Page<Word> findDueWordsByUserId(Long userId, Pageable pageable);
}