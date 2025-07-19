package com.vincennlin.collofulbackend.payload.word;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SentenceDto {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "content")
    private String content;

    @JsonProperty(value = "translation")
    private String translation;
}
