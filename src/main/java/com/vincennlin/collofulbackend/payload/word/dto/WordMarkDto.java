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

    private boolean important;

    private boolean mistaken;

    @JsonProperty(value = "review_today")
    private boolean reviewToday;
}
