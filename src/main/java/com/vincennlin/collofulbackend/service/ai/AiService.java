package com.vincennlin.collofulbackend.service.ai;

import com.vincennlin.collofulbackend.payload.word.request.*;

public interface AiService {

    String generateResponse(String message);

    CreateCollocationsForDefinitionRequest generateCollocationsForDefinition(GenerateCollocationsForDefinitionRequest request);

    CreateSentencesForCollocationRequest generateSentencesForCollocation(GenerateSentencesForCollocationRequest request);

    CreateWordWithDetailRequest generateWordFromContent(GenerateWordFromContentRequest request);
}