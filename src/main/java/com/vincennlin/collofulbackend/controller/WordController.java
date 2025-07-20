package com.vincennlin.collofulbackend.controller;

import com.vincennlin.collofulbackend.payload.constants.PageConstants;
import com.vincennlin.collofulbackend.payload.word.WordDto;
import com.vincennlin.collofulbackend.payload.word.WordPageResponse;
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

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/words")
public class WordController {

    private final WordService wordService;

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

    @PutMapping(value = {"/{word_id}"})
    public ResponseEntity<WordDto> updateWord(@Valid @RequestBody WordDto wordDto,
                                              @PathVariable(value = "word_id") Long wordId) {

        WordDto responseWordDto = wordService.updateWord(wordId, wordDto);

        return new ResponseEntity<>(responseWordDto, HttpStatus.OK);
    }

    @DeleteMapping(value = {"/{word_id}"})
    public ResponseEntity<Void> deleteWordById(@PathVariable(value = "word_id") Long wordId) {

        wordService.deleteWordById(wordId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Pageable getPageable(Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
        return PageRequest.of(pageNo, pageSize,
                sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
    }
}
