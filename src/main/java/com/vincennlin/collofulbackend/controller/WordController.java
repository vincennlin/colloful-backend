package com.vincennlin.collofulbackend.controller;

import com.vincennlin.collofulbackend.payload.constants.PageConstants;
import com.vincennlin.collofulbackend.payload.review.ReviewRequest;
import com.vincennlin.collofulbackend.payload.review.dto.ReviewInfoDto;
import com.vincennlin.collofulbackend.payload.review.dto.ReviewStateDto;
import com.vincennlin.collofulbackend.payload.word.dto.WordDto;
import com.vincennlin.collofulbackend.payload.word.dto.WordMarkDto;
import com.vincennlin.collofulbackend.payload.word.request.CreateWordWithDetailRequest;
import com.vincennlin.collofulbackend.payload.word.request.GenerateRequest;
import com.vincennlin.collofulbackend.payload.word.response.WordPageResponse;
import com.vincennlin.collofulbackend.service.ai.AiService;
import com.vincennlin.collofulbackend.service.review.ReviewService;
import com.vincennlin.collofulbackend.service.word.WordService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/words")
public class WordController {

    private final WordService wordService;
    private final ReviewService reviewService;
    private final AiService aiService;

    @GetMapping
    public ResponseEntity<WordPageResponse> getWords(
            @RequestParam(name = "pageNo", defaultValue = PageConstants.DEFAULT_PAGE_NUMBER, required = false) @Min(0) Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = PageConstants.DEFAULT_PAGE_SIZE, required = false) @Max(100) @Min(1) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = PageConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = PageConstants.DEFAULT_SORT_DIR, required = false) String sortDir) {

        Pageable pageable = getPageable(pageNo, pageSize, sortBy, sortDir);

        WordPageResponse wordPageResponse = wordService.getWords(pageable);

        return new ResponseEntity<>(wordPageResponse, HttpStatus.OK);
    }

    @GetMapping(value = {"/review"})
    public ResponseEntity<WordPageResponse> getWordsToReview(
            @RequestParam(name = "pageNo", defaultValue = PageConstants.DEFAULT_PAGE_NUMBER, required = false) @Min(0) Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = PageConstants.DEFAULT_PAGE_SIZE, required = false) @Max(100) @Min(1) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = PageConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = PageConstants.DEFAULT_SORT_DIR, required = false) String sortDir) {

        Pageable pageable = getPageable(pageNo, pageSize, sortBy, sortDir);

        WordPageResponse wordPageResponse = reviewService.getWordsToReview(pageable);

        return new ResponseEntity<>(wordPageResponse, HttpStatus.OK);
    }

    @GetMapping(value = {"/review/{word_id}/history"})
    public ResponseEntity<List<ReviewStateDto>> getReviewHistoryByWordId(@PathVariable(value = "word_id") Long wordId) {

        List<ReviewStateDto> reviewHistory = reviewService.getReviewStatesByWordId(wordId);

        return new ResponseEntity<>(reviewHistory, HttpStatus.OK);
    }

    @GetMapping(value = {"/{word_id}"})
    public ResponseEntity<WordDto> getWordById(@PathVariable(value = "word_id") Long wordId) {

        WordDto wordDto = wordService.getWordById(wordId);

        return new ResponseEntity<>(wordDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<WordDto> createWord(@Valid @RequestBody WordDto wordDto) {

        WordDto responseWordDto = wordService.createWord(wordDto);

        return new ResponseEntity<>(responseWordDto, HttpStatus.CREATED);
    }

    @PostMapping(value = {"/details"})
    public ResponseEntity<WordDto> createWordWithDetails(@Valid @RequestBody CreateWordWithDetailRequest request) {

        WordDto responseWordDto = wordService.createWordWithDetail(request.getName(), request.getDefinitions());

        return new ResponseEntity<>(responseWordDto, HttpStatus.CREATED);
    }

    @PutMapping(value = {"/{word_id}"})
    public ResponseEntity<WordDto> updateWord(@Valid @RequestBody WordDto wordDto,
                                              @PathVariable(value = "word_id") Long wordId) {

        WordDto responseWordDto = wordService.updateWord(wordId, wordDto);

        return new ResponseEntity<>(responseWordDto, HttpStatus.OK);
    }

    @PutMapping(value = {"/{word_id}/details"})
    public ResponseEntity<WordDto> updateWordWithDetails(@Valid @RequestBody CreateWordWithDetailRequest request,
                                                         @PathVariable(value = "word_id") Long wordId) {

        WordDto responseWordDto = wordService.updateWordWithDetail(wordId, request.getName(), request.getDefinitions());

        return new ResponseEntity<>(responseWordDto, HttpStatus.OK);
    }

    @PatchMapping(value = {"/{word_id}/mark"})
    public ResponseEntity<Void> updateWordMark(@Valid @RequestBody WordMarkDto wordMarkDto,
                                                  @PathVariable(value = "word_id") Long wordId) {

        wordService.updateWordMark(wordId, wordMarkDto);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = {"/{word_id}/review"})
    public ResponseEntity<WordDto> reviewWord(@Valid @RequestBody ReviewRequest request,
                                                    @PathVariable(value = "word_id") Long wordId) {

        WordDto wordDto = reviewService.reviewWord(wordId, request);

        return new ResponseEntity<>(wordDto, HttpStatus.OK);
    }

    @PostMapping(value = {"/{word_id}/review/undo"})
    public ResponseEntity<WordDto> undoReviewWord(@PathVariable(value = "word_id") Long wordId) {

        WordDto wordDto = reviewService.undoReviewWord(wordId);

        return new ResponseEntity<>(wordDto, HttpStatus.OK);
    }

    @DeleteMapping(value = {"/{word_id}"})
    public ResponseEntity<Void> deleteWordById(@PathVariable(value = "word_id") Long wordId) {

        wordService.deleteWordById(wordId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = {"/ai/generate"})
    public ResponseEntity<String> generate(@RequestBody GenerateRequest request) {

        String response = aiService.generateResponse(request.getMessage());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private Pageable getPageable(Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
        return PageRequest.of(pageNo, pageSize,
                sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
    }
}
