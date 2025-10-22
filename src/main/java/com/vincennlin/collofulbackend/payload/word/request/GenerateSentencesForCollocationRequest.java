package com.vincennlin.collofulbackend.payload.word.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.collofulbackend.entity.word.Collocation;
import com.vincennlin.collofulbackend.entity.word.Definition;
import com.vincennlin.collofulbackend.payload.word.PartOfSpeech;
import lombok.Data;

@Data
public class GenerateSentencesForCollocationRequest {

    public GenerateSentencesForCollocationRequest(Collocation collocation) {
        this.wordName = collocation.getDefinition().getWord().getName();
        this.definitionMeaning = collocation.getDefinition().getMeaning();
        this.partOfSpeech = collocation.getDefinition().getPartOfSpeech();
        this.collocationContent = collocation.getContent();
        this.collocationMeaning = collocation.getMeaning();
    }

    @JsonProperty(value = "word_name", required = true)
    private String wordName;

    @JsonProperty(value = "definition_meaning", required = true)
    private String definitionMeaning;

    @JsonProperty(value = "part_of_speech", required = true)
    private PartOfSpeech partOfSpeech;

    @JsonProperty(value = "collocation_content", required = true)
    private String collocationContent;

    @JsonProperty(value = "collocation_meaning")
    private String collocationMeaning;
}
