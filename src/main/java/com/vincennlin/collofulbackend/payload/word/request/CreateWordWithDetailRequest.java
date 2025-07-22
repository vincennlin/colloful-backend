package com.vincennlin.collofulbackend.payload.word.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.collofulbackend.payload.word.dto.DefinitionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateWordWithDetailRequest {

    public CreateWordWithDetailRequest(String name) {
        this.name = name;
        this.definitions = new ArrayList<>();
    }

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
