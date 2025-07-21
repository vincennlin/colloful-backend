package com.vincennlin.collofulbackend.controller;

import com.vincennlin.collofulbackend.entity.word.Collocation;
import com.vincennlin.collofulbackend.entity.word.Definition;
import com.vincennlin.collofulbackend.entity.word.Sentence;
import com.vincennlin.collofulbackend.entity.word.Word;
import com.vincennlin.collofulbackend.exception.ResourceRelationException;
import com.vincennlin.collofulbackend.exception.WebAPIException;
import com.vincennlin.collofulbackend.payload.constants.PageConstants;
import com.vincennlin.collofulbackend.payload.word.dto.CollocationDto;
import com.vincennlin.collofulbackend.payload.word.dto.DefinitionDto;
import com.vincennlin.collofulbackend.payload.word.dto.SentenceDto;
import com.vincennlin.collofulbackend.payload.word.dto.WordDto;
import com.vincennlin.collofulbackend.payload.word.request.CreateWordWithDetailRequest;
import com.vincennlin.collofulbackend.payload.word.request.GenerateRequest;
import com.vincennlin.collofulbackend.payload.word.response.WordPageResponse;
import com.vincennlin.collofulbackend.service.ai.AiService;
import com.vincennlin.collofulbackend.service.word.CollocationService;
import com.vincennlin.collofulbackend.service.word.DefinitionService;
import com.vincennlin.collofulbackend.service.word.SentenceService;
import com.vincennlin.collofulbackend.service.word.WordService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/words")
public class WordController {

    private final WordService wordService;
    private final DefinitionService definitionService;
    private final CollocationService collocationService;
    private final SentenceService sentenceService;
    private final AiService aiService;

    @GetMapping
    public ResponseEntity<WordPageResponse> getWords(
            @RequestParam(name = "pageNo", defaultValue = PageConstants.DEFAULT_PAGE_NUMBER, required = false) @Min(0) Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = PageConstants.DEFAULT_PAGE_SIZE, required = false) @Max(100) @Min(1) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = PageConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = PageConstants.DEFAULT_SORT_DIR, required = false) String sortDir) {

        Pageable pageable = getPageable(pageNo, pageSize, sortBy, sortDir);

        WordPageResponse wordPageResponse = wordService.getWords(pageable);

        return new ResponseEntity<>(wordPageResponse, HttpStatus.OK);
    }

    @GetMapping(value = {"/{word_id}"})
    public ResponseEntity<WordDto> getWordById(@PathVariable(value = "word_id") Long wordId) {

        WordDto wordDto = wordService.getWordById(wordId);

        return new ResponseEntity<>(wordDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<WordDto> createWord(@Valid @RequestBody WordDto wordDto) {

        WordDto responseWordDto = wordService.createWord(wordDto);

        return new ResponseEntity<>(responseWordDto, HttpStatus.CREATED);
    }

    @PostMapping(value = {"/details"})
    public ResponseEntity<WordDto> createWordWithDetails(@Valid @RequestBody CreateWordWithDetailRequest request) {

        Word word = wordService.createWordAndGetEntity(new WordDto(request.getName()));

        for (DefinitionDto definitionDto : request.getDefinitions()) {
            Definition definition = definitionService.createDefinitionAndGetEntity(definitionDto, word);

            for (CollocationDto collocationDto : definitionDto.getCollocations()) {
                Collocation collocation = collocationService.createCollocationAndGetEntity(collocationDto, definition);

                for (SentenceDto sentenceDto : collocationDto.getSentences()) {
                    Sentence savedSentence = sentenceService.createSentenceAndGetEntity(sentenceDto, collocation);
                    collocation.getSentences().add(savedSentence);
                }

                Collocation savedCollocation = collocationService.saveCollocation(collocation);
                definition.getCollocations().add(savedCollocation);
            }

            Definition savedDefinition = definitionService.saveDefinition(definition);
            word.getDefinitions().add(savedDefinition);
        }

        Word savedWord = wordService.saveWord(word);

        WordDto responseWordDto = wordService.mapToDto(savedWord);

        return new ResponseEntity<>(responseWordDto, HttpStatus.CREATED);
    }

    @PutMapping(value = {"/{word_id}"})
    public ResponseEntity<WordDto> updateWord(@Valid @RequestBody WordDto wordDto,
                                              @PathVariable(value = "word_id") Long wordId) {

        WordDto responseWordDto = wordService.updateWord(wordId, wordDto);

        return new ResponseEntity<>(responseWordDto, HttpStatus.OK);
    }

    @Transactional
    @PutMapping(value = {"/{word_id}/details"})
    public ResponseEntity<WordDto> updateWordWithDetails(@Valid @RequestBody CreateWordWithDetailRequest request,
                                                         @PathVariable(value = "word_id") Long wordId) {

        //TODO: 這裡不應該把業務邏輯寫在 Controller 中

        Word word = wordService.getWordEntityById(wordId);
        word.setName(request.getName());

        for (DefinitionDto definitionDto : request.getDefinitions()) {
            Definition definition = definitionService.updateDefinitionAndGetEntity(definitionDto.getId(), definitionDto);

            if (!definition.getWord().getId().equals(wordId)) {
                throw new ResourceRelationException("Word", wordId, "Definition", definition.getId());
            }

            for (CollocationDto collocationDto : definitionDto.getCollocations()) {
                Collocation collocation = collocationService.updateCollocationAndGetEntity(collocationDto.getId(), collocationDto);

                if (!collocation.getDefinition().getId().equals(definition.getId())) {
                    throw new ResourceRelationException("Definition", definition.getId(), "Collocation", collocation.getId());
                }

                for (SentenceDto sentenceDto : collocationDto.getSentences()) {
                    Sentence savedSentence = sentenceService.updateSentenceAndGetEntity(sentenceDto.getId(), sentenceDto);

                    if (!savedSentence.getCollocation().getId().equals(collocation.getId())) {
                        throw new ResourceRelationException("Collocation", collocation.getId(), "Sentence", savedSentence.getId());
                    }
                }

                collocationService.saveCollocation(collocation);
            }

            definitionService.saveDefinition(definition);
        }

        Word savedWord = wordService.saveWord(word);

        WordDto responseWordDto = wordService.mapToDto(savedWord);

        return new ResponseEntity<>(responseWordDto, HttpStatus.OK);
    }

    @DeleteMapping(value = {"/{word_id}"})
    public ResponseEntity<Void> deleteWordById(@PathVariable(value = "word_id") Long wordId) {

        wordService.deleteWordById(wordId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = {"/ai/generate"})
    public ResponseEntity<String> generate(@RequestBody GenerateRequest request) {

        String response = aiService.generateResponse(request.getMessage());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private Pageable getPageable(Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
        return PageRequest.of(pageNo, pageSize,
                sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
    }
}
