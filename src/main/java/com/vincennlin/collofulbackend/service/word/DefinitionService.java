package com.vincennlin.collofulbackend.service.word;

import com.vincennlin.collofulbackend.entity.word.Definition;
import com.vincennlin.collofulbackend.entity.word.Word;
import com.vincennlin.collofulbackend.payload.word.dto.DefinitionDto;

import java.util.List;

public interface DefinitionService {

    List<DefinitionDto> getDefinitionsByWord(Word word);

    DefinitionDto getDefinitionById(Long definitionId);

    Definition getDefinitionEntityById(Long definitionId);

    DefinitionDto createDefinition(DefinitionDto definitionDto, Word word);

    Definition createDefinitionAndGetEntity(DefinitionDto definitionDto, Word word);

    DefinitionDto updateDefinition(Long definitionId, DefinitionDto definitionDto);

    void deleteDefinitionById(Long definitionId);

    Definition saveDefinition(Definition definition);

    DefinitionDto mapToDto(Definition definition);
}