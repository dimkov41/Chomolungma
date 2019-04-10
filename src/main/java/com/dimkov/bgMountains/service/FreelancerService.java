package com.dimkov.bgMountains.service;

import com.dimkov.bgMountains.domain.models.service.FreelancerServiceModel;
import org.springframework.stereotype.Service;

import java.io.IOException;

public interface FreelancerService {
    boolean register(FreelancerServiceModel freelancerServiceModel,
                     String username) throws IOException;
}
