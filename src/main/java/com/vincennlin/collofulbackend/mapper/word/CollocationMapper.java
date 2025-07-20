package com.vincennlin.collofulbackend.mapper.word;

import com.vincennlin.collofulbackend.entity.word.Collocation;
import com.vincennlin.collofulbackend.payload.word.dto.CollocationDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CollocationMapper {

    private final ModelMapper modelMapper;

    public CollocationMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CollocationDto mapToDto(Collocation collocation) {
        return modelMapper.map(collocation, CollocationDto.class);
    }

    public List<CollocationDto> mapToDtoList(List<Collocation> collocations) {
        return collocations.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
