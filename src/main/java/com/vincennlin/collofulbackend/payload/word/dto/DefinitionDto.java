package com.vincennlin.collofulbackend.payload.word.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.collofulbackend.payload.word.PartOfSpeech;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefinitionDto {

    public DefinitionDto(String wordName, String meaning, PartOfSpeech partOfSpeech) {
        this.wordName = wordName;
        this.meaning = meaning;
        this.partOfSpeech = partOfSpeech;
        this.collocations = new ArrayList<>();
    }

    @JsonProperty(
            value = "id",
            access = JsonProperty.Access.READ_ONLY
    )
    private Long id;

    @JsonProperty(
            value = "word_name",
            access = JsonProperty.Access.READ_ONLY
    )
    private String wordName;

    @JsonProperty(value = "meaning", required = true)
    private String meaning;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "part_of_speech", required = true)
    private PartOfSpeech partOfSpeech;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "collocations")
    private List<CollocationDto> collocations;
}
