package com.vincennlin.collofulbackend.controller;

import com.vincennlin.collofulbackend.entity.word.Definition;
import com.vincennlin.collofulbackend.payload.word.CollocationDto;
import com.vincennlin.collofulbackend.service.word.CollocationService;
import com.vincennlin.collofulbackend.service.word.DefinitionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CollocationController {

    private final DefinitionService definitionService;
    private final CollocationService collocationService;

    @GetMapping(value = "/definitions/{definition_id}/collocations")
    public ResponseEntity<List<CollocationDto>> getCollocationsByDefinitionId(@PathVariable(value = "definition_id") Long definitionId) {

        Definition definition = definitionService.getDefinitionEntityById(definitionId);

        List<CollocationDto> collocationDtoList = collocationService.getCollocationsByDefinition(definition);

        return new ResponseEntity<>(collocationDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "/collocations/{collocation_id}")
    public ResponseEntity<CollocationDto> getCollocationById(@PathVariable(value = "collocation_id") Long collocationId) {

        CollocationDto collocationDto = collocationService.getCollocationById(collocationId);

        return new ResponseEntity<>(collocationDto, HttpStatus.OK);
    }

    @PostMapping(value = "definitions/{definition_id}/collocations")
    public ResponseEntity<CollocationDto> createCollocation(@Valid @RequestBody CollocationDto collocationDto,
                                                            @PathVariable(value = "definition_id") Long definitionId) {

        Definition definition = definitionService.getDefinitionEntityById(definitionId);

        CollocationDto responseCollocationDto = collocationService.createCollocation(collocationDto, definition);

        return new ResponseEntity<>(responseCollocationDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/collocations/{collocation_id}")
    public ResponseEntity<CollocationDto> updateCollocation(@Valid @RequestBody CollocationDto collocationDto,
                                                            @PathVariable(value = "collocation_id") Long collocationId) {

        CollocationDto responseCollocationDto = collocationService.updateCollocation(collocationId, collocationDto);

        return new ResponseEntity<>(responseCollocationDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/collocations/{collocation_id}")
    public ResponseEntity<Void> deleteCollocationById(@PathVariable(value = "collocation_id") Long collocationId) {

        collocationService.deleteCollocationById(collocationId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
