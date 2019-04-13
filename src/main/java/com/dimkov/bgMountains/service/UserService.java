package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.domain.entities.Freelancer;
import com.dimkov.bgMountains.domain.entities.User;
import com.dimkov.bgMountains.domain.models.service.UserChangeServiceModel;
import com.dimkov.bgMountains.domain.models.service.UserFreelancerServiceModel;
import com.dimkov.bgMountains.domain.models.service.UserServiceModel;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    boolean register(UserServiceModel userServiceModel);

    Optional<UserServiceModel> findByUsername(String username);
    Optional<UserServiceModel> findByEmail(String email);

    boolean setUserAuthorities(User user, String authority);

    boolean setFreelancer(Freelancer freelancer, String username);

    boolean changePassword(UserChangeServiceModel userChangeServiceModel);
}
