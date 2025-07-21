package com.vincennlin.collofulbackend.payload.review.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.collofulbackend.payload.review.ReviewOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewStateDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("review_option")
    private ReviewOption reviewOption;

    @JsonProperty("review_level")
    private Integer reviewLevel;

    @JsonProperty("review_interval")
    private Integer reviewInterval;

    @JsonProperty("last_reviewed")
    private LocalDateTime lastReviewed;

    @JsonProperty("next_review")
    private LocalDateTime nextReview;
}
