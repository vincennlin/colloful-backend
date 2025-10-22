package com.vincennlin.collofulbackend.payload.word.request;

import com.vincennlin.collofulbackend.payload.word.dto.DefinitionDto;
import com.vincennlin.collofulbackend.payload.word.dto.SentenceDto;
import lombok.Data;

import java.util.List;

@Data
public class DefinitionSentenceExample {

    public DefinitionSentenceExample(DefinitionDto definitionDto,
                                     List<SentenceDto> sentenceDtoList) {

        this.exampleInput = definitionDto;
        this.exampleOutput = new CreateSentencesForCollocationRequest(sentenceDtoList);
    }

    private DefinitionDto exampleInput;

    private CreateSentencesForCollocationRequest exampleOutput;
}
