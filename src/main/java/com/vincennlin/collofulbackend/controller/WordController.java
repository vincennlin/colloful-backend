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
@RequestMapping("/api/v1")
public class WordController {

    private final WordService wordService;

    @GetMapping(value = {"/word/{word_id}"})
    public ResponseEntity<WordDto> getWordById(@PathVariable Long word_id) {

        WordDto wordDto = wordService.getWordById(word_id);

        return new ResponseEntity<>(wordDto, HttpStatus.OK);
    }

    @PostMapping(value = {"/word"})
    public ResponseEntity<WordDto> createWord(@Valid @RequestBody WordDto wordDto) {

        WordDto responseWordDto = wordService.createWord(wordDto);

        return new ResponseEntity<>(responseWordDto, HttpStatus.CREATED);
    }

    @PostMapping(value = {"/word/{word_id}"})
    public ResponseEntity<WordDto> updateWord(@Valid @RequestBody WordDto wordDto,
                                              @PathVariable Long word_id) {

        WordDto responseWordDto = wordService.updateWord(word_id, wordDto);

        return new ResponseEntity<>(responseWordDto, HttpStatus.OK);
    }

    @DeleteMapping(value = {"/word/{word_id}"})
    public ResponseEntity<Void> deleteWordById(@PathVariable Long word_id) {

        wordService.deleteWordById(word_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
