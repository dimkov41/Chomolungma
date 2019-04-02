package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.domain.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    boolean register(UserServiceModel userServiceModel);

    Optional<UserServiceModel> findByUsername(String username);
    Optional<UserServiceModel> findByEmail(String email);
}
