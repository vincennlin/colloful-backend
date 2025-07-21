package com.vincennlin.collofulbackend.service.review;

import com.vincennlin.collofulbackend.payload.review.ReviewRequest;
import com.vincennlin.collofulbackend.payload.review.dto.ReviewStateDto;
import com.vincennlin.collofulbackend.payload.word.dto.WordDto;
import com.vincennlin.collofulbackend.payload.word.response.WordPageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {

    WordPageResponse getWordsToReview(Pageable pageable);

    List<ReviewStateDto> getReviewStatesByWordId(Long wordId);

    WordDto reviewWord(Long wordId, ReviewRequest request);

    WordDto undoReviewWord(Long wordId);
}
