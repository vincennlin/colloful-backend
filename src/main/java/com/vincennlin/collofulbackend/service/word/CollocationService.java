package com.vincennlin.collofulbackend.service.word;

import com.vincennlin.collofulbackend.entity.word.Collocation;
import com.vincennlin.collofulbackend.entity.word.Definition;
import com.vincennlin.collofulbackend.payload.word.CollocationDto;

import java.util.List;

public interface CollocationService {

    List<CollocationDto> getCollocationsByDefinition(Definition definition);

    CollocationDto getCollocationById(Long collocationId);

    Collocation getCollocationEntityById(Long collocationId);

    CollocationDto createCollocation(CollocationDto collocationDto, Definition definition);

    Collocation createCollocationAndGetEntity(CollocationDto collocationDto, Definition definition);

    CollocationDto updateCollocation(Long collocationId, CollocationDto collocationDto);

    void deleteCollocationById(Long collocationId);

    Collocation saveCollocation(Collocation collocation);
}
