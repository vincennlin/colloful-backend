package com.vincennlin.collofulbackend.payload.word;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class CollocationDto {

    @JsonProperty(
            value = "id",
            access = JsonProperty.Access.READ_ONLY
    )
    private Long id;

    @JsonProperty(value = "content", required = true)
    private String content;

    @JsonProperty(value = "meaning")
    private String meaning;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "sentences")
    private List<SentenceDto> sentences;
}
