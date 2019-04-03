package com.dimkov.bgMountains.validation;

import com.dimkov.bgMountains.domain.models.service.PeakAddServiceModel;
import com.dimkov.bgMountains.domain.models.service.PeakServiceModel;

public interface PeakValidationService {
    boolean isValid(PeakAddServiceModel peakAddServiceModel);
}
