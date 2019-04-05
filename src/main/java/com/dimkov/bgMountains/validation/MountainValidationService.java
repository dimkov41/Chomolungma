package com.dimkov.bgMountains.validation;

import com.dimkov.bgMountains.domain.models.service.MountainAddServiceModel;

public interface MountainValidationService {
    boolean isValid(MountainAddServiceModel mountainAddServiceModel);
}
