package com.dimkov.bgMountains.validation;

import com.dimkov.bgMountains.domain.models.service.FreelancerServiceModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class FreelancerValidationServiceImpl implements FreelancerValidationService {
    @Override
    public boolean isValid(FreelancerServiceModel freelancerServiceModel) {
        return freelancerServiceModel != null && ifFieldsAreEmpty(freelancerServiceModel);
    }

    private boolean ifFieldsAreEmpty(FreelancerServiceModel freelancerServiceModel){
        return freelancerServiceModel.getMobileNumber() != null &&
                !Objects.equals(freelancerServiceModel.getImage().getOriginalFilename(), "") &&
                !Objects.equals(freelancerServiceModel.getMobileNumber(), "");
    }
}
