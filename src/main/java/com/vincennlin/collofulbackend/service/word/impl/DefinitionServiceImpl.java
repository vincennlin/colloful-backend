package com.vincennlin.collofulbackend.service.word.impl;

import com.vincennlin.collofulbackend.entity.word.Definition;
import com.vincennlin.collofulbackend.entity.word.Word;
import com.vincennlin.collofulbackend.exception.ResourceNotFoundException;
import com.vincennlin.collofulbackend.exception.ResourceOwnershipException;
import com.vincennlin.collofulbackend.mapper.word.DefinitionMapper;
import com.vincennlin.collofulbackend.payload.word.DefinitionDto;
import com.vincennlin.collofulbackend.repository.word.DefinitionRepository;
import com.vincennlin.collofulbackend.service.user.UserService;
import com.vincennlin.collofulbackend.service.word.DefinitionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DefinitionServiceImpl implements DefinitionService {

    private final DefinitionMapper definitionMapper;

    private final UserService userService;

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

    @Override
    public DefinitionDto createDefinition(DefinitionDto definitionDto, Word word) {

        Definition definition = new Definition(
                definitionDto.getMeaning(), definitionDto.getPartOfSpeech(), word);

        Definition newDefinition = definitionRepository.save(definition);

        return definitionMapper.mapToDto(newDefinition);
    }

    @Override
    public DefinitionDto updateDefinition(Long definitionId, DefinitionDto definitionDto) {

        Definition definition = getDefinitionEntityById(definitionId);

        definition.setMeaning(definitionDto.getMeaning());
        definition.setPartOfSpeech(definitionDto.getPartOfSpeech());

        Definition savedDefinition = definitionRepository.save(definition);

        return definitionMapper.mapToDto(savedDefinition);
    }

    @Override
    public void deleteDefinitionById(Long definitionId) {

        Definition definition = getDefinitionEntityById(definitionId);

        Word word = definition.getWord();
        word.getDefinitions().remove(definition);

        definitionRepository.delete(definition);
    }

    private void checkDefinitionOwnership(Definition definition) {
        Long currentUserId = userService.getCurrentUser().getId();

        if (!currentUserId.equals(definition.getUserId())) {
            throw new ResourceOwnershipException(currentUserId);
        }
    }
}