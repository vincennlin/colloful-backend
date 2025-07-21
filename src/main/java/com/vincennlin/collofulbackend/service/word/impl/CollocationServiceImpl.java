package com.vincennlin.collofulbackend.service.word.impl;

import com.vincennlin.collofulbackend.entity.word.Collocation;
import com.vincennlin.collofulbackend.entity.word.Definition;
import com.vincennlin.collofulbackend.entity.word.Sentence;
import com.vincennlin.collofulbackend.exception.ResourceNotFoundException;
import com.vincennlin.collofulbackend.exception.ResourceOwnershipException;
import com.vincennlin.collofulbackend.mapper.word.CollocationMapper;
import com.vincennlin.collofulbackend.payload.word.dto.CollocationDto;
import com.vincennlin.collofulbackend.payload.word.dto.SentenceDto;
import com.vincennlin.collofulbackend.repository.word.CollocationRepository;
import com.vincennlin.collofulbackend.service.user.UserService;
import com.vincennlin.collofulbackend.service.word.CollocationService;
import com.vincennlin.collofulbackend.service.word.SentenceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class CollocationServiceImpl implements CollocationService {

    private final CollocationMapper collocationMapper;

    private final UserService userService;
    private final SentenceService sentenceService;

    private final CollocationRepository collocationRepository;

    @Override
    public List<CollocationDto> getCollocationsByDefinition(Definition definition) {

        List<Collocation> collocations = definition.getCollocations();

        return collocationMapper.mapToDtoList(collocations);
    }

    @Override
    public CollocationDto getCollocationById(Long collocationId) {
        return collocationMapper.mapToDto(getCollocationEntityById(collocationId));
    }

    @Override
    public Collocation getCollocationEntityById(Long collocationId) {

        Collocation collocation = collocationRepository.findById(collocationId)
                .orElseThrow(() -> new ResourceNotFoundException("'Collocation'", "'Id'", collocationId));

        checkCollocationOwnership(collocation);

        return collocation;
    }

    @Transactional
    @Override
    public CollocationDto createCollocation(CollocationDto collocationDto, Definition definition) {

        return collocationMapper.mapToDto(createCollocationAndGetEntity(collocationDto, definition));
    }

    @Transactional
    @Override
    public Collocation createCollocationAndGetEntity(CollocationDto collocationDto, Definition definition) {

        Collocation collocation = mapToEntity(collocationDto, definition);

        return collocationRepository.save(collocation);
    }

    @Override
    public List<Collocation> createCollocationsForDefinition(List<CollocationDto> collocationDtoList, Definition definition) {

        List<Collocation> collocations = new ArrayList<>();

        for (CollocationDto collocationDto : collocationDtoList) {

            Collocation newCollocation = collocationRepository.save(mapToEntity(collocationDto, definition));

            if (collocationDto.getSentences() != null && !collocationDto.getSentences().isEmpty()) {

                List<Sentence> sentences = sentenceService.createSentencesAndGetEntityList(collocationDto.getSentences(), newCollocation);
                newCollocation.getSentences().addAll(sentences);
            }
            collocations.add(newCollocation);
        }

        return collocationRepository.saveAll(collocations);
    }

    @Transactional
    @Override
    public CollocationDto updateCollocation(Long CollocationId, CollocationDto collocationDto) {

        return collocationMapper.mapToDto(updateCollocationAndGetEntity(CollocationId, collocationDto));
    }

    @Transactional
    @Override
    public Collocation updateCollocationAndGetEntity(Long collocationId, CollocationDto collocationDto) {

        checkCollocationDtoArguments(collocationDto);

        Collocation collocation = getCollocationEntityById(collocationId);

        collocation.setContent(collocationDto.getContent());
        collocation.setMeaning(collocationDto.getMeaning());

        if (collocationDto.getSentences() != null) {
            int size = collocationDto.getSentences().size();
            List<SentenceDto> uncreatedSentences = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                SentenceDto sentenceDto = collocationDto.getSentences().get(i);

                if (sentenceDto.getId() == null) {
                    uncreatedSentences.add(sentenceDto);
                } else {
                    Sentence updatedSentence = sentenceService.updateSentenceAndGetEntity(sentenceDto.getId(), sentenceDto);
                    collocation.getSentences().set(i, updatedSentence);
                }
            }

            if (!uncreatedSentences.isEmpty()) {
                List<Sentence> createdSentences = sentenceService.createSentencesAndGetEntityList(uncreatedSentences, collocation);
                collocation.getSentences().addAll(createdSentences);
            }
        }

        return collocationRepository.save(collocation);
    }

    @Transactional
    @Override
    public void deleteCollocationById(Long collocationId) {

        Collocation collocation = getCollocationEntityById(collocationId);

        Definition definition = collocation.getDefinition();
        definition.getCollocations().remove(collocation);

        collocationRepository.delete(collocation);
    }

    @Transactional
    @Override
    public Collocation saveCollocation(Collocation collocation) {
        return collocationRepository.save(collocation);
    }

    private void checkCollocationOwnership(Collocation collocation) {
        Long currentUserId = userService.getCurrentUser().getId();

        if (!currentUserId.equals(collocation.getUserId())) {
            throw new ResourceOwnershipException(currentUserId);
        }
    }

    private void checkCollocationDtoArguments(CollocationDto collocationDto) {
        if (collocationDto.getContent() == null || collocationDto.getContent().isBlank()) {
            throw new IllegalArgumentException("Collocation content must not be null or empty");
        }
    }

    private Collocation mapToEntity(CollocationDto collocationDto, Definition definition) {

        checkCollocationDtoArguments(collocationDto);

        return new Collocation(collocationDto.getContent(), collocationDto.getMeaning(), definition);
    }
}
