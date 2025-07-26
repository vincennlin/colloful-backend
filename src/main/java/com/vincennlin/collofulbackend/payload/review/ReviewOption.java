package com.vincennlin.collofulbackend.payload.review;

import java.time.LocalDateTime;

public enum ReviewOption implements AbstractReviewOption {
    AGAIN {
        @Override
        public Integer getInterval(Integer level) {
            return 0;
        }

        @Override
        public LocalDateTime getNextReviewTime(LocalDateTime now, Integer reviewInterval) {
            return now;
        }
    },

    HARD {
        @Override
        public Integer getInterval(Integer level) {
            return 0;
        }

        @Override
        public LocalDateTime getNextReviewTime(LocalDateTime now, Integer reviewInterval) {
            return now.plusDays(1);
        }
    },

    GOOD {
        @Override
        public Integer getInterval(Integer level) {
            return Math.max(2, level * 2);
        }
    },

    EASY {
        @Override
        public Integer getInterval(Integer level) {
            return Math.max(3, level * 3);
        }
    }
}
