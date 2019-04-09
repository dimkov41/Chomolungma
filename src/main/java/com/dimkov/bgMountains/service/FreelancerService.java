package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.domain.models.service.UserFreelancerRegisterServiceModel;

import java.io.IOException;

public interface FreelancerService {
    boolean save(UserFreelancerRegisterServiceModel userFreelancerRegisterServiceModel,
                 String username) throws IOException;
}
