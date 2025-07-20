package com.vincennlin.collofulbackend.mapper.word;

import com.vincennlin.collofulbackend.entity.word.Sentence;
import com.vincennlin.collofulbackend.payload.word.dto.SentenceDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SentenceMapper {

    private final ModelMapper modelMapper;

    public SentenceMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public SentenceDto mapToDto(Sentence sentence) {
        return modelMapper.map(sentence, SentenceDto.class);
    }

    public List<SentenceDto> mapToDtoList(List<Sentence> sentences) {
        return sentences.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
