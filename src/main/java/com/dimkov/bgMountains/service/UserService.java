package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.domain.models.service.UserFreelancerRegisterServiceModel;
import com.dimkov.bgMountains.domain.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    boolean register(UserServiceModel userServiceModel);

    boolean registerFreelancer(UserFreelancerRegisterServiceModel userFreelancerRegisterServiceModel,
                               String username) throws IOException;

    Optional<UserServiceModel> findByUsername(String username);
    Optional<UserServiceModel> findByEmail(String email);
}
