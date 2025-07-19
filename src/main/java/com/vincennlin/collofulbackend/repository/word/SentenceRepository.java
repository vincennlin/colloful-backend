package com.vincennlin.collofulbackend.repository.word;

import com.vincennlin.collofulbackend.entity.word.Sentence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SentenceRepository extends JpaRepository<Sentence, Long> {
}
