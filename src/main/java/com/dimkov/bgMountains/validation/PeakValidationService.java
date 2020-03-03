package com.dimkov.bgMountains.validation;

import com.dimkov.bgMountains.domain.models.service.PeakAddServiceModel;

public interface PeakValidationService {
    boolean isValid(PeakAddServiceModel peakAddServiceModel);
}
