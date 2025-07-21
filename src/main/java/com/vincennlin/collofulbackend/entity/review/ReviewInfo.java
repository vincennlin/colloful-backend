package com.vincennlin.collofulbackend.entity.review;

import com.vincennlin.collofulbackend.entity.word.Word;
import com.vincennlin.collofulbackend.payload.review.ReviewOption;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "review_infos")
public class ReviewInfo {

    public ReviewInfo() {
        this.reviewLevel = 0;
        this.reviewInterval = 0;
        this.lastReviewed = null;
        this.nextReview = LocalDateTime.now();
        this.reviewStates = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "review_level")
    private Integer reviewLevel;

    @Column(name = "review_interval")
    private Integer reviewInterval;

    @Column(name = "last_reviewed")
    private LocalDateTime lastReviewed;

    @Column(name = "next_review")
    private LocalDateTime nextReview;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "word_id")
    private Word word;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @JoinColumn(name = "review_info_id")
    private List<ReviewState> reviewStates;

    public void review(ReviewOption option) {
        pushReviewState(this.createState(option));

        this.reviewInterval += option.getInterval(this.reviewLevel);
        this.reviewLevel += 1;

        LocalDateTime now = LocalDateTime.now();
        this.lastReviewed = now;
        this.nextReview = now.plusDays(this.reviewInterval);
    }

    public ReviewState createState(ReviewOption option) {
        return new ReviewState(
                null,
                this.reviewLevel,
                this.reviewInterval,
                option,
                this.lastReviewed,
                this.nextReview,
                LocalDateTime.now()
        );
    }

    public void restoreState(ReviewState state) {
        this.reviewLevel = state.getReviewLevel();
        this.reviewInterval = state.getReviewInterval();
        this.lastReviewed = state.getLastReviewed();
        this.nextReview = state.getNextReview();
    }

    public void pushReviewState(ReviewState reviewState) {
        reviewStates.add(reviewState);
    }

    public ReviewState popReviewState() {
        return reviewStates.isEmpty() ? null : reviewStates.remove(reviewStates.size() - 1);
    }
}
