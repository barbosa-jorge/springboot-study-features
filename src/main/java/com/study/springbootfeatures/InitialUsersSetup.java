package com.study.springbootfeatures;

import com.study.springbootfeatures.entity.AuthorityEntity;
import com.study.springbootfeatures.entity.RoleEntity;
import com.study.springbootfeatures.entity.UserEntity;
import com.study.springbootfeatures.repository.AuthorityRepository;
import com.study.springbootfeatures.repository.RoleRepository;
import com.study.springbootfeatures.repository.UserRepository;
import com.study.springbootfeatures.shared.Roles;
import com.study.springbootfeatures.shared.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;

@Component
public class InitialUsersSetup {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Utils utils;

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("From Application ready event...");

        AuthorityEntity readAuthority = createAuthority("READ_AUTHORITY");
        AuthorityEntity writeAuthority = createAuthority("WRITE_AUTHORITY");
        AuthorityEntity deleteAuthority = createAuthority("DELETE_AUTHORITY");

        RoleEntity roleUser = createRole(Roles.ROLE_USER.name(), Arrays.asList(readAuthority,writeAuthority));

        RoleEntity roleAdmin = createRole(Roles.ROLE_ADMIN.name(),
                Arrays.asList(readAuthority,writeAuthority, deleteAuthority));

        if(roleAdmin == null) return;

        UserEntity adminUser = new UserEntity();
        adminUser.setUsername("Jorge Barbosa");
        adminUser.setEmail("admin@test.com");
        adminUser.setEncryptedPassword(bCryptPasswordEncoder.encode("12345678"));
        adminUser.setRoles(Arrays.asList(roleAdmin));
        adminUser.setUserId(utils.generateUserId());

        userRepository.save(adminUser);

        UserEntity user = new UserEntity();
        user.setUsername("Jose");
        user.setEmail("user@test.com");
        user.setEncryptedPassword(bCryptPasswordEncoder.encode("12345678"));
        user.setRoles(Arrays.asList(roleUser));
        user.setUserId(utils.generateUserId());

        userRepository.save(user);

    }

    @Transactional
    private AuthorityEntity createAuthority(String name) {

        AuthorityEntity authority = authorityRepository.findByName(name);
        if (authority == null) {
            authority = new AuthorityEntity(name);
            authorityRepository.save(authority);
        }
        return authority;
    }

    @Transactional
    private RoleEntity createRole(
            String name, Collection<AuthorityEntity> authorities) {

        RoleEntity role = roleRepository.findByName(name);
        if (role == null) {
            role = new RoleEntity(name);
            role.setAuthorities(authorities);
            roleRepository.save(role);
        }
        return role;
    }

}
