package com.vincennlin.collofulbackend.service.word.impl;

import com.vincennlin.collofulbackend.entity.word.Collocation;
import com.vincennlin.collofulbackend.entity.word.Definition;
import com.vincennlin.collofulbackend.entity.word.Word;
import com.vincennlin.collofulbackend.exception.ResourceNotFoundException;
import com.vincennlin.collofulbackend.exception.ResourceOwnershipException;
import com.vincennlin.collofulbackend.mapper.word.DefinitionMapper;
import com.vincennlin.collofulbackend.payload.word.dto.CollocationDto;
import com.vincennlin.collofulbackend.payload.word.dto.DefinitionDto;
import com.vincennlin.collofulbackend.repository.word.DefinitionRepository;
import com.vincennlin.collofulbackend.service.user.UserService;
import com.vincennlin.collofulbackend.service.word.CollocationService;
import com.vincennlin.collofulbackend.service.word.DefinitionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class DefinitionServiceImpl implements DefinitionService {

    private final DefinitionMapper definitionMapper;

    private final UserService userService;
    private final CollocationService collocationService;

    private final DefinitionRepository definitionRepository;

    @Override
    public List<DefinitionDto> getDefinitionsByWord(Word word) {

        return definitionMapper.mapToDtoList(word.getDefinitions());
    }

    @Override
    public DefinitionDto getDefinitionById(Long definitionId) {

        return definitionMapper.mapToDto(getDefinitionEntityById(definitionId));
    }

    @Override
    public Definition getDefinitionEntityById(Long definitionId) {

        Definition definition = definitionRepository.findById(definitionId)
                .orElseThrow(() -> new ResourceNotFoundException("'Definition'", "'Id'", definitionId));

        checkDefinitionOwnership(definition);

        return definition;
    }

    @Transactional
    @Override
    public DefinitionDto createDefinition(DefinitionDto definitionDto, Word word) {

        return definitionMapper.mapToDto(createDefinitionAndGetEntity(definitionDto, word));
    }

    @Transactional
    @Override
    public Definition createDefinitionAndGetEntity(DefinitionDto definitionDto, Word word) {

        Definition definition = mapToEntity(definitionDto, word);

        return definitionRepository.save(definition);
    }

    @Override
    public List<Definition> createDefinitionsForWord(List<DefinitionDto> definitionDtoList, Word word) {

        List<Definition> definitions = new ArrayList<>();

        for (DefinitionDto definitionDto : definitionDtoList) {

            Definition newDefinition = definitionRepository.save(mapToEntity(definitionDto, word));

            if (definitionDto.getCollocations() != null && !definitionDto.getCollocations().isEmpty()) {

                List<Collocation> collocations = collocationService.createCollocationsForDefinition(definitionDto.getCollocations(), newDefinition);
                newDefinition.getCollocations().addAll(collocations);
            }
            definitions.add(newDefinition);
        }

        return definitionRepository.saveAll(definitions);
    }

    @Transactional
    @Override
    public DefinitionDto updateDefinition(Long definitionId, DefinitionDto definitionDto) {

        return definitionMapper.mapToDto(updateDefinitionAndGetEntity(definitionId, definitionDto));
    }

    @Transactional
    @Override
    public Definition updateDefinitionAndGetEntity(Long definitionId, DefinitionDto definitionDto) {

        checkDefinitionDtoArguments(definitionDto);

        Definition definition = getDefinitionEntityById(definitionId);

        definition.setMeaning(definitionDto.getMeaning());
        definition.setPartOfSpeech(definitionDto.getPartOfSpeech());

        if (definitionDto.getCollocations() != null) {
            int size = definitionDto.getCollocations().size();
            List<CollocationDto> uncreatedCollocations = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                CollocationDto collocationDto = definitionDto.getCollocations().get(i);

                if (collocationDto.getId() == null) {
                    uncreatedCollocations.add(collocationDto);
                } else {
                    Collocation updatedCollocation = collocationService.updateCollocationAndGetEntity(collocationDto.getId(), collocationDto);
                    definition.getCollocations().set(i, updatedCollocation);
                }
            }

            if (!uncreatedCollocations.isEmpty()) {
                List<Collocation> createdCollocations = collocationService.createCollocationsForDefinition(uncreatedCollocations, definition);
                definition.getCollocations().addAll(createdCollocations);
            }
        }

        return definitionRepository.save(definition);
    }

    @Transactional
    @Override
    public void deleteDefinitionById(Long definitionId) {

        Definition definition = getDefinitionEntityById(definitionId);

        Word word = definition.getWord();
        word.getDefinitions().remove(definition);

        definitionRepository.delete(definition);
    }

    @Transactional
    @Override
    public Definition saveDefinition(Definition definition) {
        return definitionRepository.save(definition);
    }

    @Override
    public DefinitionDto mapToDto(Definition definition) {
        return definitionMapper.mapToDto(definition);
    }

    private void checkDefinitionOwnership(Definition definition) {
        Long currentUserId = userService.getCurrentUser().getId();

        if (!currentUserId.equals(definition.getUserId())) {
            throw new ResourceOwnershipException(currentUserId);
        }
    }

    private void checkDefinitionDtoArguments(DefinitionDto definitionDto) {
        if (definitionDto.getMeaning() == null || definitionDto.getMeaning().isBlank()) {
            throw new IllegalArgumentException("Definition meaning cannot be null or empty");
        }
        if (definitionDto.getPartOfSpeech() == null) {
            throw new IllegalArgumentException("Part of speech cannot be null");
        }
    }

    private Definition mapToEntity(DefinitionDto definitionDto, Word word) {

        checkDefinitionDtoArguments(definitionDto);

        return new Definition(definitionDto.getMeaning(), definitionDto.getPartOfSpeech(), word);
    }
}