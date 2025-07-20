package com.vincennlin.collofulbackend.controller;

import com.vincennlin.collofulbackend.payload.word.WordDto;
import com.vincennlin.collofulbackend.service.word.WordService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/words")
public class WordController {

    private final WordService wordService;

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
}
