package com.vincennlin.collofulbackend.payload.word;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SentenceDto {

    @JsonProperty(
            value = "id",
            access = JsonProperty.Access.READ_ONLY
    )
    private Long id;

    @JsonProperty(value = "content", required = true)
    private String content;

    @JsonProperty(value = "translation")
    private String translation;
}
