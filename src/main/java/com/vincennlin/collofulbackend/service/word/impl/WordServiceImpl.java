package com.vincennlin.collofulbackend.service.word.impl;

import com.vincennlin.collofulbackend.entity.word.Word;
import com.vincennlin.collofulbackend.exception.ResourceNotFoundException;
import com.vincennlin.collofulbackend.exception.ResourceOwnershipException;
import com.vincennlin.collofulbackend.mapper.word.WordMapper;
import com.vincennlin.collofulbackend.payload.word.dto.WordDto;
import com.vincennlin.collofulbackend.payload.word.response.WordPageResponse;
import com.vincennlin.collofulbackend.repository.word.WordRepository;
import com.vincennlin.collofulbackend.service.user.UserService;
import com.vincennlin.collofulbackend.service.word.WordService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class WordServiceImpl implements WordService {

    private final WordMapper wordMapper;

    private final UserService userService;

    private final WordRepository wordRepository;

    @Override
    public WordPageResponse getWords(Pageable pageable) {

        Page<Word> pageOfWords = wordRepository.findAllByUserId(userService.getCurrentUserId(), pageable);

        return getWordPageResponse(pageOfWords);
    }

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

    @Transactional
    @Override
    public WordDto createWord(WordDto wordDto) {

        return wordMapper.mapToDto(createWordAndGetEntity(wordDto));
    }

    @Transactional
    @Override
    public Word createWordAndGetEntity(WordDto wordDto) {

        Word word = new Word(userService.getCurrentUser(), wordDto.getName());

        return wordRepository.save(word);
    }

    @Transactional
    @Override
    public WordDto updateWord(Long wordId, WordDto wordDto) {

        Word word = getWordEntityById(wordId);

        if (wordDto.getName() == null || wordDto.getName().isBlank()) {
            throw new IllegalArgumentException("Word name cannot be null or empty");
        }

        word.setName(wordDto.getName());

        Word savedWord = wordRepository.save(word);

        return wordMapper.mapToDto(savedWord);
    }

    @Transactional
    @Override
    public void deleteWordById(Long wordId) {

        Word word = getWordEntityById(wordId);

        wordRepository.delete(word);
    }

    @Transactional
    @Override
    public Word saveWord(Word word) {
        return wordRepository.save(word);
    }

    @Override
    public WordDto mapToDto(Word word) {
        return wordMapper.mapToDto(word);
    }

    private void checkWordOwnership(Word word) {
        Long currentUserId = userService.getCurrentUser().getId();

        if (!currentUserId.equals(word.getUser().getId())) {
            throw new ResourceOwnershipException(currentUserId);
        }
    }

    private WordPageResponse getWordPageResponse(Page<Word> pageOfWords) {

        List<Word> words = pageOfWords.getContent();

        List<WordDto> wordDtoList = words.stream()
                .map(wordMapper::mapToDto)
                .toList();

        WordPageResponse wordPageResponse = new WordPageResponse();
        wordPageResponse.setContent(wordDtoList);
        wordPageResponse.setPageNo(pageOfWords.getNumber());
        wordPageResponse.setPageSize(pageOfWords.getSize());
        wordPageResponse.setTotalElements(pageOfWords.getTotalElements());
        wordPageResponse.setTotalPages(pageOfWords.getTotalPages());
        wordPageResponse.setLast(pageOfWords.isLast());

        return wordPageResponse;
    }
}
