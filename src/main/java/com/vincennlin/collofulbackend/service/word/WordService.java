package com.vincennlin.collofulbackend.service.word;

import com.vincennlin.collofulbackend.entity.word.Word;
import com.vincennlin.collofulbackend.payload.word.dto.DefinitionDto;
import com.vincennlin.collofulbackend.payload.word.dto.WordDto;
import com.vincennlin.collofulbackend.payload.word.dto.WordMarkDto;
import com.vincennlin.collofulbackend.payload.word.response.WordPageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WordService {

    WordPageResponse getWords(Pageable pageable);

    WordDto getWordById(Long wordId);

    Word getWordEntityById(Long wordId);

    WordDto createWord(WordDto wordDto);

    Word createWordAndGetEntity(WordDto wordDto);

    WordDto createWordWithDetail(String wordName, List<DefinitionDto> definitionDtoList);

    WordDto updateWord(Long wordId, WordDto wordDto);

    WordDto updateWordWithDetail(Long wordId, String wordName, List<DefinitionDto> definitionDtoList);

    WordDto updateWordMark(Long wordId, WordMarkDto wordMarkDto);

    void deleteWordById(Long wordId);

    Word saveWord(Word word);

    WordDto mapToDto(Word word);
}
