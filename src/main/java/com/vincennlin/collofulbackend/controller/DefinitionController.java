package com.vincennlin.collofulbackend.controller;

import com.vincennlin.collofulbackend.entity.word.Word;
import com.vincennlin.collofulbackend.payload.word.DefinitionDto;
import com.vincennlin.collofulbackend.service.word.DefinitionService;
import com.vincennlin.collofulbackend.service.word.WordService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class DefinitionController {

    private final WordService wordService;
    private final DefinitionService definitionService;

    @GetMapping(value = "/words/{word_id}/definitions")
    public ResponseEntity<List<DefinitionDto>> getDefinitionsByWordId(@PathVariable(value = "word_id") Long wordId) {

        Word word = wordService.getWordEntityById(wordId);

        List<DefinitionDto> definitionDtoList = definitionService.getDefinitionsByWord(word);

        return new ResponseEntity<>(definitionDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "/definitions/{definition_id}")
    public ResponseEntity<DefinitionDto> getDefinitionById(@PathVariable(value = "definition_id") Long definitionId) {

        DefinitionDto definitionDto = definitionService.getDefinitionById(definitionId);

        return new ResponseEntity<>(definitionDto, HttpStatus.OK);
    }

    @PostMapping(value = "/words/{word_id}/definitions")
    public ResponseEntity<DefinitionDto> createDefinition(@Valid @RequestBody DefinitionDto definitionDto,
                                                          @PathVariable(value = "word_id") Long wordId) {

        Word word = wordService.getWordEntityById(wordId);

        DefinitionDto responseDefinitionDto = definitionService.createDefinition(definitionDto, word);

        return new ResponseEntity<>(responseDefinitionDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/definitions/{definition_id}")
    public ResponseEntity<DefinitionDto> updateDefinition(@Valid @RequestBody DefinitionDto definitionDto,
                                                          @PathVariable(value = "definition_id") Long definitionId) {

        DefinitionDto responseDefinitionDto = definitionService.updateDefinition(definitionId, definitionDto);

        return new ResponseEntity<>(responseDefinitionDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/definitions/{definition_id}")
    public ResponseEntity<Void> deleteDefinitionById(@PathVariable(value = "definition_id") Long definitionId) {

        definitionService.deleteDefinitionById(definitionId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}