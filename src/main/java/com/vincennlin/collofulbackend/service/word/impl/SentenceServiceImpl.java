package com.vincennlin.collofulbackend.service.word.impl;

import com.vincennlin.collofulbackend.entity.word.Collocation;
import com.vincennlin.collofulbackend.entity.word.Sentence;
import com.vincennlin.collofulbackend.exception.ResourceNotFoundException;
import com.vincennlin.collofulbackend.exception.ResourceOwnershipException;
import com.vincennlin.collofulbackend.mapper.word.SentenceMapper;
import com.vincennlin.collofulbackend.payload.word.dto.SentenceDto;
import com.vincennlin.collofulbackend.repository.word.SentenceRepository;
import com.vincennlin.collofulbackend.service.user.UserService;
import com.vincennlin.collofulbackend.service.word.SentenceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class SentenceServiceImpl implements SentenceService {

    private final SentenceMapper sentenceMapper;

    private final UserService userService;

    private final SentenceRepository sentenceRepository;

    @Override
    public List<SentenceDto> getSentencesByCollocation(Collocation collocation) {
        return sentenceMapper.mapToDtoList(collocation.getSentences());
    }

    @Override
    public SentenceDto getSentenceById(Long sentenceId) {
        return sentenceMapper.mapToDto(getSentenceEntityById(sentenceId));
    }

    @Override
    public Sentence getSentenceEntityById(Long sentenceId) {

        Sentence sentence = sentenceRepository.findById(sentenceId)
                .orElseThrow(() -> new ResourceNotFoundException("'Sentence'", "'Id'", sentenceId));

        checkSentenceOwnership(sentence);

        return sentence;
    }

    @Transactional
    @Override
    public SentenceDto createSentence(SentenceDto sentenceDto, Collocation collocation) {

        return sentenceMapper.mapToDto(createSentenceAndGetEntity(sentenceDto, collocation));
    }

    @Transactional
    @Override
    public Sentence createSentenceAndGetEntity(SentenceDto sentenceDto, Collocation collocation) {

        Sentence sentence = mapToEntity(sentenceDto, collocation);

        return sentenceRepository.save(sentence);
    }

    @Override
    public List<Sentence> createSentencesAndGetEntityList(List<SentenceDto> sentenceDtoList, Collocation collocation) {

        List<Sentence> sentences = sentenceDtoList.stream()
                .map(sentenceDto -> mapToEntity(sentenceDto, collocation))
                .toList();

        return sentenceRepository.saveAll(sentences);
    }

    @Transactional
    @Override
    public SentenceDto updateSentence(Long sentenceId, SentenceDto sentenceDto) {

        return sentenceMapper.mapToDto(updateSentenceAndGetEntity(sentenceId, sentenceDto));
    }

    @Transactional
    @Override
    public Sentence updateSentenceAndGetEntity(Long sentenceId, SentenceDto sentenceDto) {

        checkSentenceDtoArguments(sentenceDto);

        Sentence sentence = getSentenceEntityById(sentenceId);

        sentence.setContent(sentenceDto.getContent());
        sentence.setTranslation(sentenceDto.getTranslation());

        return sentenceRepository.save(sentence);
    }

    @Transactional
    @Override
    public void deleteSentenceById(Long sentenceId) {

        Sentence sentence = getSentenceEntityById(sentenceId);

        Collocation collocation = sentence.getCollocation();
        collocation.getSentences().remove(sentence);

        sentenceRepository.delete(sentence);
    }

    private void checkSentenceOwnership(Sentence sentence) {
        Long currentUserId = userService.getCurrentUserId();

        if (!currentUserId.equals(sentence.getUserId())) {
            throw new ResourceOwnershipException(currentUserId);
        }
    }

    private void checkSentenceDtoArguments(SentenceDto sentenceDto) {
        if (sentenceDto.getContent() == null || sentenceDto.getContent().isBlank()) {
            throw new IllegalArgumentException("Content must not be null or empty");
        }
    }

    private Sentence mapToEntity(SentenceDto sentenceDto, Collocation collocation) {

        checkSentenceDtoArguments(sentenceDto);

        return new Sentence(sentenceDto.getContent(), sentenceDto.getTranslation(), collocation);
    }
}
