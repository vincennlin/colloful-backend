package com.vincennlin.collofulbackend.mapper.word;

import com.vincennlin.collofulbackend.entity.word.Definition;
import com.vincennlin.collofulbackend.payload.word.DefinitionDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DefinitionMapper {

    private final ModelMapper modelMapper;

    public DefinitionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DefinitionDto mapToDto(Definition definition) {
        return modelMapper.map(definition, DefinitionDto.class);
    }
}
