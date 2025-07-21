package com.vincennlin.collofulbackend.entity.review;

import com.vincennlin.collofulbackend.payload.review.ReviewOption;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "review_states")
public class ReviewState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "review_level")
    private Integer reviewLevel;

    @Column(name = "review_interval")
    private Integer reviewInterval;

    @Enumerated(EnumType.STRING)
    @Column(name = "review_option")
    private ReviewOption reviewOption;

    @Column(name = "last_reviewed")
    private LocalDateTime lastReviewed;

    @Column(name = "next_review")
    private LocalDateTime nextReview;

    @CreationTimestamp
    @Column(name = "date_reviewed")
    private LocalDateTime dateReviewed;
}