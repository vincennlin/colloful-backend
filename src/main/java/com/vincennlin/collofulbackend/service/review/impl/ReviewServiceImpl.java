package com.vincennlin.collofulbackend.service.review.impl;

import com.vincennlin.collofulbackend.entity.review.ReviewInfo;
import com.vincennlin.collofulbackend.entity.review.ReviewState;
import com.vincennlin.collofulbackend.entity.word.Word;
import com.vincennlin.collofulbackend.exception.WebAPIException;
import com.vincennlin.collofulbackend.payload.review.ReviewRequest;
import com.vincennlin.collofulbackend.payload.review.dto.ReviewStateDto;
import com.vincennlin.collofulbackend.payload.word.dto.WordDto;
import com.vincennlin.collofulbackend.repository.review.ReviewInfoRepository;
import com.vincennlin.collofulbackend.service.review.ReviewService;
import com.vincennlin.collofulbackend.service.user.UserService;
import com.vincennlin.collofulbackend.service.word.WordService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ModelMapper modelMapper;

    private final UserService userService;
    private final WordService wordService;

    private final ReviewInfoRepository reviewInfoRepository;

    @Override
    public List<WordDto> getWordsToReview() {

        Long userId = userService.getCurrentUserId();

        List<Word> dueWords = reviewInfoRepository.findDueWordsByUserId(userId);

        return dueWords.stream().map(wordService::mapToDto).toList();
    }

    @Override
    public List<ReviewStateDto> getReviewStatesByWordId(Long wordId) {

        Word word = wordService.getWordEntityById(wordId);

        List<ReviewState> reviewStates = word.getReviewInfo().getReviewStates();

        if (reviewStates.isEmpty()) {
            throw new WebAPIException(HttpStatus.NOT_FOUND, "No review states found for 'Word' with 'Id' " + wordId);
        }

        return reviewStates.stream().map(this::mapReviewStateToDto).toList();
    }

    @Override
    public WordDto reviewWord(Long wordId, ReviewRequest request) {

        Word word = wordService.getWordEntityById(wordId);

        word.review(request.getReviewOption());

        Word updatedWord = wordService.saveWord(word);

        return wordService.mapToDto(updatedWord);
    }

    @Override
    public WordDto undoReviewWord(Long wordId) {

        Word word = wordService.getWordEntityById(wordId);

        ReviewInfo reviewInfo = word.getReviewInfo();

        if (reviewInfo.getReviewStates().isEmpty()) {
            throw new WebAPIException(HttpStatus.BAD_REQUEST, "Cannot undo Review for 'Word' with 'Id' " + wordId + " because there is no review state to undo");
        }

        ReviewState lastReviewState = reviewInfo.popReviewState();

        reviewInfo.restoreState(lastReviewState);

        Word updatedWord = wordService.saveWord(word);

        return wordService.mapToDto(updatedWord);
    }

    public ReviewStateDto mapReviewStateToDto(ReviewState reviewState) {
        return modelMapper.map(reviewState, ReviewStateDto.class);
    }
}
