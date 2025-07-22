package com.vincennlin.collofulbackend.payload.word.request;

import com.vincennlin.collofulbackend.payload.word.dto.DefinitionDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WordExample {

    public WordExample(String name) {
        this.name = name;
        this.definitions = new ArrayList<>();
    }

    private String name;

    private List<DefinitionDto> definitions;
}
