package com.vincennlin.collofulbackend.service.word.impl;

import com.vincennlin.collofulbackend.entity.review.ReviewInfo;
import com.vincennlin.collofulbackend.entity.word.Definition;
import com.vincennlin.collofulbackend.entity.word.Word;
import com.vincennlin.collofulbackend.exception.ResourceNotFoundException;
import com.vincennlin.collofulbackend.exception.ResourceOwnershipException;
import com.vincennlin.collofulbackend.mapper.word.WordMapper;
import com.vincennlin.collofulbackend.payload.word.dto.DefinitionDto;
import com.vincennlin.collofulbackend.payload.word.dto.WordDto;
import com.vincennlin.collofulbackend.payload.word.dto.WordMarkDto;
import com.vincennlin.collofulbackend.payload.word.response.WordPageResponse;
import com.vincennlin.collofulbackend.repository.word.WordRepository;
import com.vincennlin.collofulbackend.service.user.UserService;
import com.vincennlin.collofulbackend.service.word.DefinitionService;
import com.vincennlin.collofulbackend.service.word.WordService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class WordServiceImpl implements WordService {

    private final WordMapper wordMapper;

    private final UserService userService;
    private final DefinitionService definitionService;

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

        ReviewInfo reviewInfo = new ReviewInfo();

        reviewInfo.setWord(word);
        word.setReviewInfo(reviewInfo);

        return wordRepository.save(word);
    }

    @Transactional
    @Override
    public WordDto createWordWithDetail(String wordName, List<DefinitionDto> definitionDtoList) {

        Word newWord = wordRepository.save(createWordAndGetEntity(new WordDto(wordName)));

        if (definitionDtoList.isEmpty()) {
            return mapToDto(newWord);
        }

        List<Definition> savedDefinitions = definitionService.createDefinitionsForWord(definitionDtoList, newWord);
        newWord.setDefinitions(savedDefinitions);

        return wordMapper.mapToDto(wordRepository.save(newWord));
    }

    @Transactional
    @Override
    public WordDto updateWord(Long wordId, WordDto wordDto) {

        checkWordDtoArguments(wordDto);

        Word word = getWordEntityById(wordId);

        word.setName(wordDto.getName());
        word.setImportant(wordDto.isImportant());
        word.setMistaken(wordDto.isMistaken());
        word.setReviewToday(wordDto.isReviewToday());

        if (wordDto.getDefinitions() != null) {
            int size = wordDto.getDefinitions().size();
            List<DefinitionDto> uncreatedDefinitions = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                DefinitionDto definitionDto = wordDto.getDefinitions().get(i);

                if (definitionDto.getId() == null) {
                    uncreatedDefinitions.add(definitionDto);
                } else {
                    Definition updatedDefinition = definitionService.updateDefinitionAndGetEntity(definitionDto.getId(), definitionDto);
                    word.getDefinitions().set(i, updatedDefinition);
                }
            }

            if (!uncreatedDefinitions.isEmpty()) {
                List<Definition> createdDefinitions = definitionService.createDefinitionsForWord(uncreatedDefinitions, word);
                word.getDefinitions().addAll(createdDefinitions);
            }
        }

        return wordMapper.mapToDto(wordRepository.save(word));
    }

    @Transactional
    @Override
    public WordDto updateWordWithDetail(Long wordId, String wordName, List<DefinitionDto> definitionDtoList) {

        WordDto wordDto = new WordDto(wordName);
        wordDto.setDefinitions(definitionDtoList);

        return updateWord(wordId, wordDto);
    }

    @Transactional
    @Override
    public void updateWordMark(Long wordId, WordMarkDto wordMarkDto) {

        Word word = getWordEntityById(wordId);

        if (wordMarkDto.getImportant() != null) {
            word.setImportant(wordMarkDto.getImportant());
        }
        if (wordMarkDto.getMistaken() != null) {
            word.setMistaken(wordMarkDto.getMistaken());
        }
        if (wordMarkDto.getReviewToday() != null) {
            word.setReviewToday(wordMarkDto.getReviewToday());
        }

        wordRepository.save(word);

//        return wordMapper.mapToDto(wordRepository.save(word));
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

    private void checkWordDtoArguments(WordDto wordDto) {
        if (wordDto.getName() == null || wordDto.getName().isBlank()) {
            throw new IllegalArgumentException("Word name must not be null or empty");
        }
    }

    private Word mapToEntity(WordDto wordDto) {

        checkWordDtoArguments(wordDto);

        return new Word(userService.getCurrentUser(), wordDto.getName());
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
