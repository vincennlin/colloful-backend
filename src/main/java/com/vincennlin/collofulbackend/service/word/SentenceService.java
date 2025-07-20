package com.vincennlin.collofulbackend.service.word;

import com.vincennlin.collofulbackend.entity.word.Collocation;
import com.vincennlin.collofulbackend.entity.word.Sentence;
import com.vincennlin.collofulbackend.payload.word.SentenceDto;

import java.util.List;

public interface SentenceService {

    List<SentenceDto> getSentencesByCollocation(Collocation collocation);

    SentenceDto getSentenceById(Long sentenceId);

    Sentence getSentenceEntityById(Long sentenceId);

    SentenceDto createSentence(SentenceDto sentenceDto, Collocation collocation);

    Sentence createSentenceAndGetEntity(SentenceDto sentenceDto, Collocation collocation);

    SentenceDto updateSentence(Long sentenceId, SentenceDto sentenceDto);

    void deleteSentenceById(Long sentenceId);
}
