package com.vincennlin.collofulbackend.payload.word.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SentenceDto {

    public SentenceDto(String content, String translation) {
        this.content = content;
        this.translation = translation;
    }

    @JsonProperty(
            value = "id"
//            access = JsonProperty.Access.READ_ONLY
    )
    private Long id;

    @JsonProperty(value = "content", required = true)
    @NotEmpty
    private String content;

    @JsonProperty(value = "translation")
    private String translation;
}
