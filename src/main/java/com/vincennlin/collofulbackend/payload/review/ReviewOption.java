package com.vincennlin.collofulbackend.payload.review;

public enum ReviewOption implements AbstractReviewOption {
    AGAIN {
        @Override
        public Integer getInterval(Integer level) {
            return 0;
        }
    },

    HARD {
        @Override
        public Integer getInterval(Integer level) {
            return 1;
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
