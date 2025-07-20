package com.vincennlin.collofulbackend.service.word;

import com.vincennlin.collofulbackend.entity.word.Word;
import com.vincennlin.collofulbackend.payload.word.WordDto;

public interface WordService {

    WordDto getWordById(Long wordId);

    Word getWordEntityById(Long wordId);

    WordDto createWord(WordDto wordDto);

    WordDto updateWord(Long wordId, WordDto wordDto);

    void deleteWordById(Long wordId);
}
