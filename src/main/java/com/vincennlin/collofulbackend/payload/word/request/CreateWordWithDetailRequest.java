package com.vincennlin.collofulbackend.payload.word.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.collofulbackend.payload.word.dto.DefinitionDto;
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
