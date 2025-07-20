package com.vincennlin.collofulbackend;

import com.vincennlin.collofulbackend.payload.user.Roles;
import com.vincennlin.collofulbackend.entity.user.Authority;
import com.vincennlin.collofulbackend.entity.user.Role;
import com.vincennlin.collofulbackend.entity.user.User;
import com.vincennlin.collofulbackend.repository.user.AuthorityRepository;
import com.vincennlin.collofulbackend.repository.user.RoleRepository;
import com.vincennlin.collofulbackend.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Component
public class InitialUsersSetup {

    private AuthorityRepository authorityRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(InitialUsersSetup.class);

    @Transactional
    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) {
//        logger.info("Application ready event triggered from InitialUsersSetup.");
//
//        Authority readAuthority = createAuthority("READ");
//        Authority createAuthority = createAuthority("CREATE");
//        Authority updateAuthority = createAuthority("UPDATE");
//        Authority deleteAuthority = createAuthority("DELETE");
//        Authority advancedAuthority = createAuthority("ADVANCED");
//
//        Set<Authority> basicAuthorities = Set.of(readAuthority, createAuthority, updateAuthority, deleteAuthority);
//        Set<Authority> advancedAuthorities = Set.of(readAuthority, createAuthority, updateAuthority, deleteAuthority, advancedAuthority);
//
//        createRole(Roles.ROLE_USER.name(), basicAuthorities);
//        Role adminRole = createRole(Roles.ROLE_ADMIN.name(), advancedAuthorities);
//
//        if (adminRole == null) return;
//
//        User admin = new User();
//        admin.setName("admin");
//        admin.setUsername("admin");
//        admin.setEmail("admin@gmail.com");
//        admin.setPassword(passwordEncoder.encode("admin"));
//        admin.setRoles(Set.of(roleRepository.findByName(Roles.ROLE_ADMIN.name())));
//
//        Optional<User> storedAdmin = userRepository.findByEmail(admin.getEmail());
//
//        if (storedAdmin.isEmpty()) {
//            userRepository.save(admin);
//        }
    }

    @Transactional
    public Authority createAuthority(String name) {

        Authority authority = authorityRepository.findByName(name);

        if (authority == null) {
            authority = new Authority();
            authority.setName(name);
            authorityRepository.save(authority);
        }
        return authority;
    }

    @Transactional
    public Role createRole(String name, Set<Authority> authorities) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            role.setAuthorities(authorities);
            role = roleRepository.save(role);
        }
        return role;
    }

}
