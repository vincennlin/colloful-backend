package com.vincennlin.collofulbackend.mapper.word;

import com.vincennlin.collofulbackend.entity.word.Definition;
import com.vincennlin.collofulbackend.payload.word.dto.DefinitionDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DefinitionMapper {

    private final ModelMapper modelMapper;

    public DefinitionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DefinitionDto mapToDto(Definition definition) {
        DefinitionDto definitionDto = modelMapper.map(definition, DefinitionDto.class);
        definitionDto.setWordName(definition.getWord().getName());
        return definitionDto;
    }

    public List<DefinitionDto> mapToDtoList(List<Definition> definitions) {
        return definitions.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
