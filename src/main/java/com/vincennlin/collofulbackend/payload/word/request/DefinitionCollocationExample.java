package com.vincennlin.collofulbackend.payload.word.request;

import com.vincennlin.collofulbackend.payload.word.dto.CollocationDto;
import com.vincennlin.collofulbackend.payload.word.dto.DefinitionDto;
import lombok.Data;

import java.util.List;

@Data
public class DefinitionCollocationExample {

    public DefinitionCollocationExample(DefinitionDto definitionDto,
                                        List<CollocationDto> collocationDtoList) {

        this.exampleInput = definitionDto;
        this.exampleOutput = new CreateCollocationsForDefinitionRequest(collocationDtoList);
    }

    private DefinitionDto exampleInput;

    private CreateCollocationsForDefinitionRequest exampleOutput;
}
