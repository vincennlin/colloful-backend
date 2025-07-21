package com.vincennlin.collofulbackend.payload.word.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WordMarkDto {

    @JsonProperty(required = false)
    private Boolean important;

    @JsonProperty(required = false)
    private Boolean mistaken;

    @JsonProperty(value = "review_today", required = false)
    private Boolean reviewToday;
}
