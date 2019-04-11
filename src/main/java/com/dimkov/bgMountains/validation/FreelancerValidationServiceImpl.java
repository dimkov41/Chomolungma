package com.dimkov.bgMountains.validation;

import com.dimkov.bgMountains.domain.models.service.FreelancerAddServiceModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class FreelancerValidationServiceImpl implements FreelancerValidationService {
    @Override
    public boolean isValid(FreelancerAddServiceModel freelancerAddServiceModel) {
        return freelancerAddServiceModel != null && ifFieldsAreEmpty(freelancerAddServiceModel);
    }

    private boolean ifFieldsAreEmpty(FreelancerAddServiceModel freelancerAddServiceModel){
        return freelancerAddServiceModel.getMobileNumber() != null &&
                !Objects.equals(freelancerAddServiceModel.getImage().getOriginalFilename(), "") &&
                !Objects.equals(freelancerAddServiceModel.getMobileNumber(), "");
    }
}
