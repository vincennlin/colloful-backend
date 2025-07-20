package com.vincennlin.collofulbackend.payload.word;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.collofulbackend.payload.word.partofspeech.PartOfSpeech;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WordDto {

    @JsonProperty(
            value = "id",
            access = JsonProperty.Access.READ_ONLY
    )
    private Long id;

    @JsonProperty(
            value = "user_id",
            access = JsonProperty.Access.READ_ONLY
    )
    private Long userId;

    @JsonProperty(value = "name", required = true)
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "part_of_speech")
    private PartOfSpeech partOfSpeech;

    @JsonProperty(value = "definitions")
    private List<DefinitionDto> definitions;
}
