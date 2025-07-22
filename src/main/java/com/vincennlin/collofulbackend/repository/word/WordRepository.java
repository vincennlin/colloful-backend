package com.vincennlin.collofulbackend.repository.word;

import com.vincennlin.collofulbackend.entity.word.Word;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WordRepository extends JpaRepository<Word, Long> {

    Page<Word> findAllByUserId(Long userId, Pageable pageable);

    Optional<Word> findByNameAndUserId(String wordName, Long userId);

    Page<Word> searchByNameContainingAndUserId(String wordName, Long userId, Pageable pageable);
}
