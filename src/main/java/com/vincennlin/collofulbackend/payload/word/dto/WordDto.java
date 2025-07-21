package com.vincennlin.collofulbackend.payload.word.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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
            value = "id"
//            access = JsonProperty.Access.READ_ONLY
    )
    private Long id;

    @JsonProperty(
            value = "user_id",
            access = JsonProperty.Access.READ_ONLY
    )
    private Long userId;

    @JsonProperty(value = "name", required = true)
    @NotEmpty
    private String name;

    @JsonProperty()
    private boolean important;

    @JsonProperty()
    private boolean mistaken;

    @JsonProperty(value = "review_today")
    private boolean reviewToday;

    @JsonProperty(
            value = "date_created",
            access = JsonProperty.Access.READ_ONLY
    )
    private LocalDateTime dateCreated;

    @JsonProperty(
            value = "last_updated",
            access = JsonProperty.Access.READ_ONLY
    )
    private LocalDateTime lastUpdated;

    @JsonProperty(
            value = "review_level",
            access = JsonProperty.Access.READ_ONLY
    )
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer reviewLevel;

    @JsonProperty(
            value = "review_interval",
            access = JsonProperty.Access.READ_ONLY
    )
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer reviewInterval;

    @JsonProperty(
            value = "last_reviewed",
            access = JsonProperty.Access.READ_ONLY
    )
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime lastReviewed;

    @JsonProperty(
            value = "next_review",
            access = JsonProperty.Access.READ_ONLY
    )
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime nextReview;

    @JsonProperty(value = "definitions")
    private List<DefinitionDto> definitions;
}
