package com.vincennlin.collofulbackend.repository.user;

import com.vincennlin.collofulbackend.entity.user.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByName(String name);
}
