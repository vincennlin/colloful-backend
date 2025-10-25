package com.vincennlin.collofulbackend.payload.review;

import java.time.LocalDateTime;

public enum ReviewOption implements AbstractReviewOption {
    AGAIN {
        @Override
        public Integer getNextInterval(Integer interval) {
            return 0;
        }

        @Override
        public LocalDateTime getNextReviewTime(LocalDateTime now, Integer reviewInterval) {
            return now;
        }
    },

    HARD {
        @Override
        public Integer getNextInterval(Integer interval) {
            return interval / 2;
        }

        @Override
        public LocalDateTime getNextReviewTime(LocalDateTime now, Integer reviewInterval) {
            return now.plusDays(1);
        }
    },

    GOOD {
        @Override
        public Integer getNextInterval(Integer interval) {
            return Math.max(2, interval * 2);
        }
    },

    EASY {
        @Override
        public Integer getNextInterval(Integer interval) {
            return Math.max(3, interval * 3);
        }
    }
}
