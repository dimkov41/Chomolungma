package com.dimkov.bgMountains.validation;

import com.dimkov.bgMountains.domain.models.service.PeakAddServiceModel;
import com.dimkov.bgMountains.domain.models.service.PeakServiceModel;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class PeakValidationServiceImpl implements PeakValidationService {
    @Override
    public boolean isValid(PeakAddServiceModel peakAddServiceModel) {
        return peakAddServiceModel != null && ifFieldsAreEmpty(peakAddServiceModel);
    }


    private boolean ifFieldsAreEmpty(PeakAddServiceModel peakAddServiceModel){
        return peakAddServiceModel.getName() != null &&
                peakAddServiceModel.getElevation() != 0 &&
                !peakAddServiceModel.getImage().getOriginalFilename().equals("") &&
                peakAddServiceModel.getMountainName() != null &&
                peakAddServiceModel.getDescription() != null;
    }
}
