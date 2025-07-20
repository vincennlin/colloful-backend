package com.vincennlin.collofulbackend.service.ai;

import com.vincennlin.collofulbackend.payload.word.request.CreateCollocationsForDefinitionRequest;
import com.vincennlin.collofulbackend.payload.word.request.GenerateCollocationsForDefinitionRequest;

public interface AiService {

    String generateResponse(String message);

    CreateCollocationsForDefinitionRequest generateCollocationsForDefinition(GenerateCollocationsForDefinitionRequest request);
}