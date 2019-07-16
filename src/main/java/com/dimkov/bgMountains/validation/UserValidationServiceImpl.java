package com.dimkov.bgMountains.validation;

import com.dimkov.bgMountains.domain.models.service.UserChangeServiceModel;
import org.springframework.stereotype.Component;

@Component
public class UserValidationServiceImpl implements UserValidationService {
    @Override
    public boolean isChangeModelValid(UserChangeServiceModel userChangeServiceModel) {
        return userChangeServiceModel != null;
    }
}
