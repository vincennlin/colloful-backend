package com.vincennlin.collofulbackend.mapper.word;

import com.vincennlin.collofulbackend.entity.word.Collocation;
import com.vincennlin.collofulbackend.payload.word.CollocationDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CollocationMapper {

    private final ModelMapper modelMapper;

    public CollocationMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CollocationDto mapToDto(Collocation collocation) {
        return modelMapper.map(collocation, CollocationDto.class);
    }
}
