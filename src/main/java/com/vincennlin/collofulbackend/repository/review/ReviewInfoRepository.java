package com.vincennlin.collofulbackend.repository.review;

import com.vincennlin.collofulbackend.entity.review.ReviewInfo;
import com.vincennlin.collofulbackend.entity.word.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewInfoRepository extends JpaRepository<ReviewInfo, Long> {

    @Query("SELECT w FROM Word w JOIN w.reviewInfo r WHERE w.user.id = :userId AND r.nextReview < CURRENT_TIMESTAMP")
    List<Word> findDueWordsByUserId(Long userId);
}