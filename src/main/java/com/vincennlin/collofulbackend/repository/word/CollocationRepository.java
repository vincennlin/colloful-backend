package com.vincennlin.collofulbackend.repository.word;

import com.vincennlin.collofulbackend.entity.word.Collocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollocationRepository extends JpaRepository<Collocation, Long> {
}
