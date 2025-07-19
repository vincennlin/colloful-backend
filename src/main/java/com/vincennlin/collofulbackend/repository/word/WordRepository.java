package com.vincennlin.collofulbackend.repository.word;

import com.vincennlin.collofulbackend.entity.word.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<Word, Long> {
}
