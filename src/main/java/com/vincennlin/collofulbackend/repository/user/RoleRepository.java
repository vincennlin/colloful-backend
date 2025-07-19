package com.vincennlin.collofulbackend.repository.user;

import com.vincennlin.collofulbackend.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
