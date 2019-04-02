package com.dimkov.bgMountains.config;

import com.dimkov.bgMountains.domain.entities.Role;
import com.dimkov.bgMountains.repository.RoleRepository;
import com.dimkov.bgMountains.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseSeeder {
    private final RoleRepository roleRepository;

    @Autowired
    public DatabaseSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void seed() {
        if (this.roleRepository.findAll().isEmpty()) {
            Role userRole = new Role(Constants.ROLE_USER);

            Role freelancerRole = new Role(Constants.ROLE_FREELANCER);

            Role moderatorRole = new Role(Constants.ROLE_MODERATOR);

            Role adminRole = new Role(Constants.ROLE_ADMIN);

            Role rootRole = new Role(Constants.ROLE_ROOT);

            this.roleRepository.save(userRole);
            this.roleRepository.save(freelancerRole);
            this.roleRepository.save(moderatorRole);
            this.roleRepository.save(adminRole);
            this.roleRepository.save(rootRole);
        }
    }
}
