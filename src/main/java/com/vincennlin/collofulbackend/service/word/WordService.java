package com.vincennlin.collofulbackend.service.word;

import com.vincennlin.collofulbackend.entity.word.Word;
import com.vincennlin.collofulbackend.payload.word.WordDto;
import com.vincennlin.collofulbackend.payload.word.WordPageResponse;
import org.springframework.data.domain.Pageable;

public interface WordService {

    WordPageResponse getWords(Pageable pageable);

    WordDto getWordById(Long wordId);

    Word getWordEntityById(Long wordId);

    WordDto createWord(WordDto wordDto);

    Word createWordAndGetEntity(WordDto wordDto);

    WordDto updateWord(Long wordId, WordDto wordDto);

    void deleteWordById(Long wordId);

    Word saveWord(Word word);

    WordDto mapToDto(Word word);
}
