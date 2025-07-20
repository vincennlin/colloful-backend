package com.vincennlin.collofulbackend.payload.word.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    public WordDto(String name) {
        this.name = name;
    }

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

    @JsonProperty(value = "definitions")
    private List<DefinitionDto> definitions;
}
