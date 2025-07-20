package com.vincennlin.collofulbackend.payload.word;

import lombok.Data;

import java.util.List;

@Data
public class CreateCollocationsForDefinitionRequest {

    private List<CollocationDto> collocations;
}
