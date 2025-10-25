package com.vincennlin.collofulbackend.payload.review;

import java.time.LocalDateTime;

public interface AbstractReviewOption {

    Integer getNextInterval(Integer interval);

    default LocalDateTime getNextReviewTime(LocalDateTime now, Integer reviewInterval) {
        return now.plusDays(reviewInterval);
    }
}
