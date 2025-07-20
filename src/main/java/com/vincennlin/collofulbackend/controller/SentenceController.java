package com.vincennlin.collofulbackend.controller;


import com.vincennlin.collofulbackend.entity.word.Collocation;
import com.vincennlin.collofulbackend.payload.word.dto.SentenceDto;
import com.vincennlin.collofulbackend.service.word.CollocationService;
import com.vincennlin.collofulbackend.service.word.SentenceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class SentenceController {

    private final CollocationService collocationService;
    private final SentenceService sentenceService;

    @GetMapping(value = "/collocations/{collocation_id}/sentences")
    public ResponseEntity<List<SentenceDto>> getSentencesByCollocationId(@PathVariable("collocation_id") Long collocationId) {

        Collocation collocation = collocationService.getCollocationEntityById(collocationId);

        List<SentenceDto> sentenceDtoList = sentenceService.getSentencesByCollocation(collocation);

        return new ResponseEntity<>(sentenceDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "/sentences/{sentence_id}")
    public ResponseEntity<SentenceDto> getSentenceById(@PathVariable("sentence_id") Long sentenceId) {

        SentenceDto sentenceDto = sentenceService.getSentenceById(sentenceId);

        return new ResponseEntity<>(sentenceDto, HttpStatus.OK);
    }

    @PostMapping(value = "/collocations/{collocation_id}/sentences")
    public ResponseEntity<SentenceDto> createSentence(@Valid @RequestBody SentenceDto sentenceDto,
                                                      @PathVariable("collocation_id") Long collocationId) {

        Collocation collocation = collocationService.getCollocationEntityById(collocationId);

        SentenceDto responseSentenceDto = sentenceService.createSentence(sentenceDto, collocation);

        return new ResponseEntity<>(responseSentenceDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/sentences/{sentence_id}")
    public ResponseEntity<SentenceDto> updateSentence(@Valid @RequestBody SentenceDto sentenceDto,
                                                      @PathVariable("sentence_id") Long sentenceId) {

        SentenceDto responseSentenceDto = sentenceService.updateSentence(sentenceId, sentenceDto);

        return new ResponseEntity<>(responseSentenceDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/sentences/{sentence_id}")
    public ResponseEntity<Void> deleteSentenceById(@PathVariable("sentence_id") Long sentenceId) {

        sentenceService.deleteSentenceById(sentenceId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
