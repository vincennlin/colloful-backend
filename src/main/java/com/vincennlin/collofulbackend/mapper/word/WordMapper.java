package com.vincennlin.collofulbackend.mapper.word;

import com.vincennlin.collofulbackend.entity.word.Word;
import com.vincennlin.collofulbackend.payload.word.dto.WordDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class WordMapper {

    private final ModelMapper modelMapper;

    private WordMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public WordDto mapToDto(Word word) {
        WordDto wordDto = modelMapper.map(word, WordDto.class);
        wordDto.setReviewLevel(word.getReviewInfo().getReviewLevel());
        wordDto.setReviewInterval(word.getReviewInfo().getReviewInterval());
        if (word.getReviewInfo().getLastReviewed() != null) {
            wordDto.setLastReviewed(word.getReviewInfo().getLastReviewed());
        }
        if (word.getReviewInfo().getNextReview() != null) {
            wordDto.setNextReview(word.getReviewInfo().getNextReview());
        }
        return wordDto;
    }
}
