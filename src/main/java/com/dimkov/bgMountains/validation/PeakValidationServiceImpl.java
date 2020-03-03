package com.dimkov.bgMountains.validation;

import com.dimkov.bgMountains.domain.models.service.PeakAddServiceModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PeakValidationServiceImpl implements PeakValidationService {
    @Override
    public boolean isValid(PeakAddServiceModel peakAddServiceModel) {
        return peakAddServiceModel != null && ifFieldsAreEmpty(peakAddServiceModel);
    }


    private boolean ifFieldsAreEmpty(PeakAddServiceModel peakAddServiceModel){
        return peakAddServiceModel.getName() != null &&
                peakAddServiceModel.getElevation() != 0 &&
                !Objects.equals(peakAddServiceModel.getImage().getOriginalFilename(), "") &&
                peakAddServiceModel.getMountainName() != null &&
                peakAddServiceModel.getDescription() != null;
    }
}
