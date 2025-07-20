package com.vincennlin.collofulbackend.payload.word;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CreateWordWithDetailRequest {

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
