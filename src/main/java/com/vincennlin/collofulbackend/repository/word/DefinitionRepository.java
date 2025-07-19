package com.vincennlin.collofulbackend.repository.word;

import com.vincennlin.collofulbackend.entity.word.Definition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefinitionRepository extends JpaRepository<Definition, Long> {
}
