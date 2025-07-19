package com.vincennlin.collofulbackend.service.word.impl;

import com.vincennlin.collofulbackend.entity.word.Word;
import com.vincennlin.collofulbackend.mapper.word.WordMapper;
import com.vincennlin.collofulbackend.payload.word.WordDto;
import com.vincennlin.collofulbackend.repository.word.WordRepository;
import com.vincennlin.collofulbackend.service.user.UserService;
import com.vincennlin.collofulbackend.service.word.WordService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class WordServiceImpl implements WordService {

    private final WordMapper wordMapper;

    private final UserService userService;

    private final WordRepository wordRepository;

    @Override
    public WordDto getWordById(Long wordId) {
        return wordMapper.mapToDto(wordRepository.findById(wordId).orElse(null));
    }

    @Override
    public WordDto createWord(WordDto wordDto) {

        Word word = new Word(userService.getCurrentUser(), wordDto.getName(), wordDto.getPartOfSpeech());

        Word newWord = wordRepository.save(word);

        return wordMapper.mapToDto(newWord);
    }

    @Override
    public WordDto updateWord(Long wordId, WordDto wordDto) {
        return null;
    }

    @Override
    public void deleteWordById(Long wordId) {

    }
}
