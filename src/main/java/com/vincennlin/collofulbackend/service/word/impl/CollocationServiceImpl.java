package com.vincennlin.collofulbackend.service.word.impl;

import com.vincennlin.collofulbackend.entity.word.Collocation;
import com.vincennlin.collofulbackend.entity.word.Definition;
import com.vincennlin.collofulbackend.exception.ResourceNotFoundException;
import com.vincennlin.collofulbackend.exception.ResourceOwnershipException;
import com.vincennlin.collofulbackend.mapper.word.CollocationMapper;
import com.vincennlin.collofulbackend.payload.word.CollocationDto;
import com.vincennlin.collofulbackend.repository.word.CollocationRepository;
import com.vincennlin.collofulbackend.service.user.UserService;
import com.vincennlin.collofulbackend.service.word.CollocationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CollocationServiceImpl implements CollocationService {

    private final CollocationMapper collocationMapper;

    private final UserService userService;

    private final CollocationRepository collocationRepository;

    @Override
    public List<CollocationDto> getCollocationsByDefinition(Definition definition) {

        List<Collocation> collocations = definition.getCollocations();

        return collocationMapper.mapToDtoList(collocations);
    }

    @Override
    public CollocationDto getCollocationById(Long collocationId) {
        return collocationMapper.mapToDto(getCollocationEntityById(collocationId));
    }

    @Override
    public Collocation getCollocationEntityById(Long collocationId) {

        Collocation collocation = collocationRepository.findById(collocationId)
                .orElseThrow(() -> new ResourceNotFoundException("'Collocation'", "'Id'", collocationId));

        checkCollocationOwnership(collocation);

        return collocation;
    }

    @Override
    public CollocationDto createCollocation(CollocationDto collocationDto, Definition definition) {

        return collocationMapper.mapToDto(createCollocationAndGetEntity(collocationDto, definition));
    }

    @Override
    public Collocation createCollocationAndGetEntity(CollocationDto collocationDto, Definition definition) {

        Collocation collocation = new Collocation(collocationDto.getContent(), collocationDto.getMeaning(), definition);

        return collocationRepository.save(collocation);
    }

    @Override
    public CollocationDto updateCollocation(Long CollocationId, CollocationDto collocationDto) {

        Collocation collocation = getCollocationEntityById(CollocationId);

        collocation.setContent(collocationDto.getContent());
        collocation.setMeaning(collocationDto.getMeaning());

        Collocation savedCollocation = collocationRepository.save(collocation);

        return collocationMapper.mapToDto(savedCollocation);
    }

    @Override
    public void deleteCollocationById(Long collocationId) {

        Collocation collocation = getCollocationEntityById(collocationId);

        Definition definition = collocation.getDefinition();
        definition.getCollocations().remove(collocation);

        collocationRepository.delete(collocation);
    }

    @Override
    public Collocation saveCollocation(Collocation collocation) {
        return collocationRepository.save(collocation);
    }

    private void checkCollocationOwnership(Collocation collocation) {
        Long currentUserId = userService.getCurrentUser().getId();

        if (!currentUserId.equals(collocation.getUserId())) {
            throw new ResourceOwnershipException(currentUserId);
        }
    }
}
