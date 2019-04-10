package com.dimkov.bgMountains.validation;

import com.dimkov.bgMountains.domain.models.service.FreelancerServiceModel;

public interface FreelancerValidationService {
    boolean isValid(FreelancerServiceModel freelancerServiceModel);
}
