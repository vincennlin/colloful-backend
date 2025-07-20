package com.vincennlin.collofulbackend.service.word.impl;

import com.vincennlin.collofulbackend.entity.word.Word;
import com.vincennlin.collofulbackend.exception.ResourceNotFoundException;
import com.vincennlin.collofulbackend.exception.ResourceOwnershipException;
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

        return wordMapper.mapToDto(getWordEntityById(wordId));
    }

    @Override
    public Word getWordEntityById(Long wordId) {

        Word word = wordRepository.findById(wordId)
                .orElseThrow(() -> new ResourceNotFoundException("'Word'", "'Id'", wordId));

        checkWordOwnership(word);

        return word;
    }

    @Override
    public WordDto createWord(WordDto wordDto) {

        Word word = new Word(userService.getCurrentUser(), wordDto.getName());

        Word newWord = wordRepository.save(word);

        return wordMapper.mapToDto(newWord);
    }

    @Override
    public WordDto updateWord(Long wordId, WordDto wordDto) {

        Word word = getWordEntityById(wordId);

        word.setName(wordDto.getName());

        Word savedWord = wordRepository.save(word);

        return wordMapper.mapToDto(savedWord);
    }

    @Override
    public void deleteWordById(Long wordId) {

        Word word = getWordEntityById(wordId);

        wordRepository.delete(word);
    }

    private void checkWordOwnership(Word word) {
        Long currentUserId = userService.getCurrentUser().getId();

        if (!currentUserId.equals(word.getUser().getId())) {
            throw new ResourceOwnershipException(currentUserId);
        }
    }
}
