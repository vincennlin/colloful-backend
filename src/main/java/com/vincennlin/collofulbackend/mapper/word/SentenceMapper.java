package com.vincennlin.collofulbackend.mapper.word;

import com.vincennlin.collofulbackend.entity.word.Sentence;
import com.vincennlin.collofulbackend.payload.word.SentenceDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SentenceMapper {

    private final ModelMapper modelMapper;

    public SentenceMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public SentenceDto mapToDto(Sentence sentence) {
        return modelMapper.map(sentence, SentenceDto.class);
    }
}
