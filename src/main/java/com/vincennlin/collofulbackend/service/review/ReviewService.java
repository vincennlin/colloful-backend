package com.vincennlin.collofulbackend.service.review;

import com.vincennlin.collofulbackend.payload.review.ReviewRequest;
import com.vincennlin.collofulbackend.payload.review.dto.ReviewStateDto;
import com.vincennlin.collofulbackend.payload.word.dto.WordDto;

import java.util.List;

public interface ReviewService {

    List<WordDto> getWordsToReview();

    List<ReviewStateDto> getReviewStatesByWordId(Long wordId);

    WordDto reviewWord(Long wordId, ReviewRequest request);

    WordDto undoReviewWord(Long wordId);
}
