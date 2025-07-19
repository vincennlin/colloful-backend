package com.vincennlin.collofulbackend.controller;

import com.vincennlin.collofulbackend.payload.word.WordDto;
import com.vincennlin.collofulbackend.service.word.WordService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class WordController {

    private final WordService wordService;

    @PostMapping(value = {"/word"})
    public ResponseEntity<WordDto> createWord(@Valid @RequestBody WordDto wordDto) {

        WordDto responseWordDto = wordService.createWord(wordDto);
        return new ResponseEntity<>(responseWordDto, HttpStatus.CREATED);
    }
}
