package com.vincennlin.collofulbackend.payload.word;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.collofulbackend.payload.word.partofspeech.SubPart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefinitionDto {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "meaning")
    private String meaning;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "sub_part")
    private SubPart subPart;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "collocations")
    private List<CollocationDto> collocations;
}
