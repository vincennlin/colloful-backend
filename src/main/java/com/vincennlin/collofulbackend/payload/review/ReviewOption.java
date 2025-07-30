package com.vincennlin.collofulbackend.payload.review;

import java.time.LocalDateTime;

public enum ReviewOption implements AbstractReviewOption {
    AGAIN {
        @Override
        public Integer getIntervalIncrement(Integer interval) {
            return 0;
        }

        @Override
        public LocalDateTime getNextReviewTime(LocalDateTime now, Integer reviewInterval) {
            return now;
        }
    },

    HARD {
        @Override
        public Integer getIntervalIncrement(Integer interval) {
            return 0;
        }

        @Override
        public LocalDateTime getNextReviewTime(LocalDateTime now, Integer reviewInterval) {
            return now.plusDays(1);
        }
    },

    GOOD {
        @Override
        public Integer getIntervalIncrement(Integer interval) {
            return Math.max(2, interval);
        }
    },

    EASY {
        @Override
        public Integer getIntervalIncrement(Integer interval) {
            return Math.max(3, interval * 2);
        }
    }
}
