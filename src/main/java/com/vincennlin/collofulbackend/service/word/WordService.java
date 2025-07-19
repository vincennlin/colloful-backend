package com.vincennlin.collofulbackend.service.word;

import com.vincennlin.collofulbackend.payload.word.WordDto;

public interface WordService {

    WordDto getWordById(Long wordId);

    WordDto createWord(WordDto wordDto);

    WordDto updateWord(Long wordId, WordDto wordDto);

    void deleteWordById(Long wordId);
}
