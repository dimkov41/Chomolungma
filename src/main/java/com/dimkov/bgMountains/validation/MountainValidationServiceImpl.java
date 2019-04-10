package com.dimkov.bgMountains.validation;

import com.dimkov.bgMountains.domain.models.service.MountainAddServiceModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MountainValidationServiceImpl implements MountainValidationService {
    @Override
    public boolean isValid(MountainAddServiceModel mountainAddServiceModel) {
        return mountainAddServiceModel != null && ifFieldsAreEmpty(mountainAddServiceModel);

    }

    private boolean ifFieldsAreEmpty(MountainAddServiceModel mountainAddServiceModel){
        return mountainAddServiceModel.getName() != null &&
                !Objects.equals(mountainAddServiceModel.getImage().getOriginalFilename(), "") &&
                mountainAddServiceModel.getDescription() != null;
    }
}
