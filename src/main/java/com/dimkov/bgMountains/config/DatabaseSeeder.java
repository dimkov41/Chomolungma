package com.dimkov.bgMountains.config;

import com.dimkov.bgMountains.domain.entities.Authority;
import com.dimkov.bgMountains.repository.RoleRepository;
import com.dimkov.bgMountains.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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
            Authority userAuthority = new Authority(Constants.ROLE_USER);

            Authority freelancerAuthority = new Authority(Constants.ROLE_FREELANCER);

            Authority moderatorAuthority = new Authority(Constants.ROLE_MODERATOR);

            Authority adminAuthority = new Authority(Constants.ROLE_ADMIN);

            Authority rootAuthority = new Authority(Constants.ROLE_ROOT);

            this.roleRepository.save(userAuthority);
            this.roleRepository.save(freelancerAuthority);
            this.roleRepository.save(moderatorAuthority);
            this.roleRepository.save(adminAuthority);
            this.roleRepository.save(rootAuthority);
        }
    }
}
