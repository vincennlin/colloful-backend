package com.vincennlin.collofulbackend.service.ai;

import com.vincennlin.collofulbackend.payload.word.request.CreateCollocationsForDefinitionRequest;
import com.vincennlin.collofulbackend.payload.word.request.CreateWordWithDetailRequest;
import com.vincennlin.collofulbackend.payload.word.request.GenerateCollocationsForDefinitionRequest;
import com.vincennlin.collofulbackend.payload.word.request.GenerateWordFromContentRequest;

public interface AiService {

    String generateResponse(String message);

    CreateCollocationsForDefinitionRequest generateCollocationsForDefinition(GenerateCollocationsForDefinitionRequest request);

    CreateWordWithDetailRequest generateWordFromContent(GenerateWordFromContentRequest request);
}