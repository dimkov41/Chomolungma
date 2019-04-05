package com.dimkov.bgMountains.validation;

import com.dimkov.bgMountains.domain.models.service.MountainAddServiceModel;
import org.springframework.stereotype.Component;

@Component
public class MountainValidationServiceImpl implements MountainValidationService {
    @Override
    public boolean isValid(MountainAddServiceModel mountainAddServiceModel) {
        return mountainAddServiceModel != null && ifFieldsAreEmpty(mountainAddServiceModel);

    }

    private boolean ifFieldsAreEmpty(MountainAddServiceModel mountainAddServiceModel){
        return mountainAddServiceModel.getName() != null &&
                !mountainAddServiceModel.getImage().getOriginalFilename().equals("") &&
                mountainAddServiceModel.getDescription() != null;
    }
}
