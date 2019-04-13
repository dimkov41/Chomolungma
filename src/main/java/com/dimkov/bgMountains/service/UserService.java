package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.domain.entities.Freelancer;
import com.dimkov.bgMountains.domain.entities.User;
import com.dimkov.bgMountains.domain.models.service.FreelancerServiceModel;
import com.dimkov.bgMountains.domain.models.service.UserChangeServiceModel;
import com.dimkov.bgMountains.domain.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;
import java.util.Set;

public interface UserService extends UserDetailsService {
    boolean register(UserServiceModel userServiceModel);

    Optional<UserServiceModel> findByUsername(String username);
    Optional<UserServiceModel> findByEmail(String email);

    boolean setUserAuthorities(User user, String authority);

    boolean setFreelancer(Freelancer freelancer, String username);

    Set<FreelancerServiceModel> getHiredFreelancers(String username);

    boolean changePassword(UserChangeServiceModel userChangeServiceModel);
}
