package com.vincennlin.collofulbackend.payload.word.request;

import com.vincennlin.collofulbackend.payload.word.dto.CollocationDto;
import lombok.Data;

import java.util.List;

@Data
public class CreateCollocationsForDefinitionRequest {

    private List<CollocationDto> collocations;
}
