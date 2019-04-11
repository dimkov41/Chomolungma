package com.dimkov.bgMountains.validation;

import com.dimkov.bgMountains.domain.models.service.FreelancerAddServiceModel;

public interface FreelancerValidationService {
    boolean isValid(FreelancerAddServiceModel freelancerAddServiceModel);
}
