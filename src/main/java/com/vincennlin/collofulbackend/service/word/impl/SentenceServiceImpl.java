package com.vincennlin.collofulbackend.service.word.impl;

import com.vincennlin.collofulbackend.entity.word.Collocation;
import com.vincennlin.collofulbackend.entity.word.Sentence;
import com.vincennlin.collofulbackend.exception.ResourceNotFoundException;
import com.vincennlin.collofulbackend.exception.ResourceOwnershipException;
import com.vincennlin.collofulbackend.mapper.word.SentenceMapper;
import com.vincennlin.collofulbackend.payload.word.SentenceDto;
import com.vincennlin.collofulbackend.repository.word.SentenceRepository;
import com.vincennlin.collofulbackend.service.user.UserService;
import com.vincennlin.collofulbackend.service.word.SentenceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

    @Override
    public SentenceDto createSentence(SentenceDto sentenceDto, Collocation collocation) {

        Sentence sentence = new Sentence(sentenceDto.getContent(), sentenceDto.getTranslation(),  collocation);

        Sentence newSentence = sentenceRepository.save(sentence);

        return sentenceMapper.mapToDto(newSentence);
    }

    @Override
    public SentenceDto updateSentence(Long sentenceId, SentenceDto sentenceDto) {

        Sentence sentence = getSentenceEntityById(sentenceId);

        sentence.setContent(sentenceDto.getContent());
        sentence.setTranslation(sentenceDto.getTranslation());

        Sentence savedSentence = sentenceRepository.save(sentence);

        return sentenceMapper.mapToDto(savedSentence);
    }

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
}
