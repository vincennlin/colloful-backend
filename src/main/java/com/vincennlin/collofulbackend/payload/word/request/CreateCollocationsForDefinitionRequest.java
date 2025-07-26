package com.vincennlin.collofulbackend.payload.word.request;

import com.vincennlin.collofulbackend.payload.word.dto.CollocationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCollocationsForDefinitionRequest {

    private List<CollocationDto> collocations;
}
