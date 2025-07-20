package com.vincennlin.collofulbackend.payload.word.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.collofulbackend.entity.word.Definition;
import com.vincennlin.collofulbackend.payload.word.PartOfSpeech;
import lombok.Data;

@Data
public class GenerateCollocationsForDefinitionRequest {

    public GenerateCollocationsForDefinitionRequest(Definition definition) {
        this.wordName = definition.getWord().getName();
        this.meaning = definition.getMeaning();
        this.partOfSpeech = definition.getPartOfSpeech();
    }

    @JsonProperty(value = "word_name", required = true)
    private String wordName;

    @JsonProperty(value = "meaning", required = true)
    private String meaning;

    @JsonProperty(value = "part_of_speech", required = true)
    private PartOfSpeech partOfSpeech;
}
